package com.techframer.janusgraph.api.crud.graph.transaction;

import com.techframer.janusgraph.api.crud.graph.transaction.exceptions.TransactionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tinkerpop.gremlin.structure.Graph;

import java.security.SecureRandom;
import java.util.UUID;

public class GraphTransaction {

	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(GraphTransaction.class);

	private static final int COMMIT_RETRY_COUNT = 3;

	/** Threaded Tinkerpop transaction. */
	private Graph threadedTransaction;

	/** UUID of transaction */
	private UUID id;
	
	/**
	 * Creates a new transaction instance.
	 *
	 * @param aGraphInstance
	 *            - Instance of the graph to request the transaction from.
	 */
	public GraphTransaction(Graph aGraphInstance) {
		super();

		if (!aGraphInstance.features().graph().supportsTransactions()) {
			throw new UnsupportedOperationException();
		}

		// Request a threaded transaction object from the graph.
		this.threadedTransaction = aGraphInstance.tx().createThreadedTx();
		// Create a unique identifier for this transaction.
		this.id = UUID.randomUUID();
		logger.info("Open transaction - id: " + id);
	}

	public String id() {
		return id.toString();
	}

	public Graph getGraphInstance() {
		return threadedTransaction;
	}

	public void commit() throws TransactionException {

		logger.debug("Commiting transaction - " + id);

		final long initialBackoff = (int) (new SecureRandom().nextDouble() * 50);

		// If something goes wrong, we will retry a couple of times before
		// giving up.
		for (int i = 0; i < COMMIT_RETRY_COUNT; i++) {

			try {

				// Do the commit.
				threadedTransaction.tx().commit();
				logger.info("Committed transaction - id: " + id);
				return;

			} catch (Throwable e) {

				logger.debug("Transaction " + id + " failed to commit due to: " + e.getMessage());

				// Have we used up all of our retries?
				if (i == COMMIT_RETRY_COUNT - 1) {

					logger.error("Maxed out commit attempt retries, client must handle exception and retry. "
							+ e.getMessage());
					threadedTransaction.tx().rollback();
					throw new TransactionException(e);
				}

				// Calculate how long we will wait before retrying...
				final long backoff = (long) Math.pow(2, i) * initialBackoff;
				logger.warn("Caught exception while retrying transaction commit, retrying in " + backoff + " ms");

				// ...and sleep before trying the commit again.
				try {
					Thread.sleep(backoff);

				} catch (InterruptedException ie) {

					logger.info("Interrupted while backing off on transaction commit");
					Thread.currentThread().interrupt();
					return;
				}
			}
		}
	}

	public void rollback() throws TransactionException {

		long initialBackoff = (int) (new SecureRandom().nextDouble() * 50);

		// If something goes wrong, we will retry a couple of times before
		// giving up.
		for (int i = 0; i < COMMIT_RETRY_COUNT; i++) {

			try {

				threadedTransaction.tx().rollback();
				logger.info("Rolled back transaction - id: " + id);
				return;

			} catch (Throwable e) {

				logger.debug("Transaction " + id + " failed to roll back due to: " + e.getMessage());

				// Have we used up all of our retries?
				if (i == COMMIT_RETRY_COUNT - 1) {

					logger.error("Maxed out rollback attempt retries, client must handle exception and retry. "
							+ e.getMessage());
					throw new TransactionException(e);
				}

				// Calculate how long we will wait before retrying...
				final long backoff = (long) Math.pow(2, i) * initialBackoff;
				logger.warn("Caught exception while retrying transaction roll back, retrying in " + backoff + " ms");

				// ...and sleep before trying the commit again.
				try {
					Thread.sleep(backoff);

				} catch (InterruptedException ie) {

					logger.info("Interrupted while backing off on transaction rollback");
					Thread.currentThread().interrupt();
					return;
				}
			}
		}
	}
	
}
