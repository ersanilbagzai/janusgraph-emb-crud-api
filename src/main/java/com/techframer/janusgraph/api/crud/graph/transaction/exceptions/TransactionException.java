package com.techframer.janusgraph.api.crud.graph.transaction.exceptions;

/**
 * The Class GraphTransactionException.
 */
public class TransactionException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5492385992668214838L;

	/**
	 * Instantiates a new graph transaction exception.
	 */
	public TransactionException() {
	}

	/**
	 * Instantiates a new graph transaction exception.
	 *
	 * @param message
	 *            the message
	 */
	public TransactionException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new graph transaction exception.
	 *
	 * @param cause
	 *            the cause
	 */
	public TransactionException(Throwable cause) {
		super(cause);
	}

	/**
	 * Instantiates a new graph transaction exception.
	 *
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public TransactionException(String message, Throwable cause) {
		super(message, cause);
	}
}
