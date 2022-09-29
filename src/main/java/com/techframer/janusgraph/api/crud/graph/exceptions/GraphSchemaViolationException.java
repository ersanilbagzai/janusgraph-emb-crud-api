
package com.techframer.janusgraph.api.crud.graph.exceptions;

/**
 * The Class GraphSchemaViolationException.
 */
public final class GraphSchemaViolationException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -6650058224577965021L;

	/**
	 * Instantiates a new graph schema violation exception.
	 */
	public GraphSchemaViolationException() {
	}

	/**
	 * Instantiates a new graph schema violation exception.
	 *
	 * @param message
	 *            the message
	 */
	public GraphSchemaViolationException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new graph schema violation exception.
	 *
	 * @param cause
	 *            the cause
	 */
	public GraphSchemaViolationException(Throwable cause) {
		super(cause);
	}

	/**
	 * Instantiates a new graph schema violation exception.
	 *
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public GraphSchemaViolationException(String message, Throwable cause) {
		super(message, cause);
	}
}
