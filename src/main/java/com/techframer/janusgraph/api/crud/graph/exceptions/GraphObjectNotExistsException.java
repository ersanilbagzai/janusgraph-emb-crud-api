
package com.techframer.janusgraph.api.crud.graph.exceptions;

/**
 * The Class GraphObjectNotExistsException.
 */
public final class GraphObjectNotExistsException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4365365939154593814L;

	/**
	 * Instantiates a new graph object not exists exception.
	 */
	public GraphObjectNotExistsException() {
	}

	/**
	 * Instantiates a new graph object not exists exception.
	 *
	 * @param message
	 *            the message
	 */
	public GraphObjectNotExistsException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new graph object not exists exception.
	 *
	 * @param cause
	 *            the cause
	 */
	public GraphObjectNotExistsException(Throwable cause) {
		super(cause);
	}

	/**
	 * Instantiates a new graph object not exists exception.
	 *
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public GraphObjectNotExistsException(String message, Throwable cause) {
		super(message, cause);
	}
}
