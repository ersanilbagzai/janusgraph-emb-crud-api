package com.techframer.janusgraph.api.crud.graph.transaction.impl;

import com.techframer.janusgraph.api.crud.graph.connection.IGraphInstanceManager;
import com.techframer.janusgraph.api.crud.graph.transaction.GraphTransaction;
import com.techframer.janusgraph.api.crud.graph.transaction.exceptions.TransactionException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.janusgraph.core.JanusGraph;
import org.janusgraph.core.schema.JanusGraphManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("GraphTransactionManagerImpl")
public class GraphTransactionManagerImpl implements IGraphTransactionManager{

	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(GraphTransactionManagerImpl.class);

	@Autowired
	IGraphInstanceManager graphInstance;
	
	
	
	@Override
	public GraphTransaction openTransaction() throws TransactionException {
		return new GraphTransaction(graphInstance.getGraphInstance());
	}

	@Override
	public GraphTransaction getTransactionIfNull(GraphTransaction transaction) throws TransactionException {
		if (transaction == null) {
			return new GraphTransaction(graphInstance.getGraphInstance());
		} else {
			return transaction;
		}
	}
	
	@Override
	public void commitTransaction(GraphTransaction transaction) throws TransactionException {
		try {
			// Commit the transaction.
			transaction.commit();
			logger.info("Transaction committed for id :{}", transaction.toString());

		} catch (TransactionException e) {
			logger.error("Error while Transaction commit for id : {} ", transaction.id());
			throw e;
		}
	}
	
	/**
	 * Commit transaction.
	 *
	 * @param createThreadedTx the create threaded tx
	 * @throws TransactionException the graph transaction exception
	 */
	@Override
	public void commitTransaction(Graph createThreadedTx) throws TransactionException {
		try {
			createThreadedTx.tx().commit();
		} catch (Exception e) {
			createThreadedTx.tx().rollback();
			logger.error("Exception in commitTransaction : {}", ExceptionUtils.getStackTrace(e));
			throw new TransactionException();
		}
	}
	
	/**
	 * Gets the threaded transaction.
	 *
	 * @return the threaded transaction
	 * @throws TransactionException the graph transaction exception
	 */
	@Override
	public Graph getThreadedTransaction() throws TransactionException {
		try {
			Graph graphInstance = this.graphInstance.getGraphInstance();
			return graphInstance.tx().createThreadedTx();
		} catch (Exception e) {
			logger.error("Exception in getThreadedTransaction : {} ", ExceptionUtils.getStackTrace(e));
			throw new TransactionException("Can not get/create new threaded transaction");
		}
	}

	@Override
	public Graph getThreadedTransaction(GraphTransaction transaction) throws TransactionException {
		try {
			if (transaction == null) {
				return getThreadedTransaction();
			} else {
				return transaction.getGraphInstance();
			}
		} catch (Exception e) {
			logger.error("Exception in getThreadedTransaction : {} ", ExceptionUtils.getStackTrace(e));
			throw new TransactionException("Can not get/create new threaded transaction");
		}
	}
	
	@Override
	public void rollbackTransaction(GraphTransaction transaction) throws TransactionException {
		logger.info("Going to rollaback transaction id :{}", transaction.id());
		transaction.rollback();
	}
	
	@Override
	public JanusGraphManagement getManagementTransaction(JanusGraph graphInstance) throws TransactionException {
		try {
			return graphInstance.openManagement();
		} catch (Exception ex) {
			logger.error("Exception in openManagementTransaction : {}",ExceptionUtils.getStackTrace(ex));
			throw new TransactionException("Can not get/create new management transaction");
		}
	}
}
