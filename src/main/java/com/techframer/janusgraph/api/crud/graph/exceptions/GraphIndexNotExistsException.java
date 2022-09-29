
package com.techframer.janusgraph.api.crud.graph.exceptions;

/**
 * The Class GraphIndexNotExistsException.
 */
public final class GraphIndexNotExistsException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1690478892404278379L;

	/**
	 * Instantiates a new graph index not exists exception.
	 */
	public GraphIndexNotExistsException() {
	}

	/**
	 * Instantiates a new graph index not exists exception.
	 *
	 * @param message
	 *            the message
	 */
	public GraphIndexNotExistsException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new graph index not exists exception.
	 *
	 * @param cause
	 *            the cause
	 */
	public GraphIndexNotExistsException(Throwable cause) {
		super(cause);
	}

	/**
	 * Instantiates a new graph index not exists exception.
	 *
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public GraphIndexNotExistsException(String message, Throwable cause) {
		super(message, cause);
	}
}
