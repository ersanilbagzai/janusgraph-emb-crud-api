
package com.techframer.janusgraph.api.crud.graph.exceptions;

/**
 * The Class GraphMarshallingException.
 */
public final class GraphMarshallingException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7962962670920382670L;

	/**
	 * Instantiates a new graph marshalling exception.
	 */
	public GraphMarshallingException() {
	}

	/**
	 * Instantiates a new graph marshalling exception.
	 *
	 * @param message
	 *            the message
	 */
	public GraphMarshallingException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new graph marshalling exception.
	 *
	 * @param cause
	 *            the cause
	 */
	public GraphMarshallingException(Throwable cause) {
		super(cause);
	}

	/**
	 * Instantiates a new graph marshalling exception.
	 *
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public GraphMarshallingException(String message, Throwable cause) {
		super(message, cause);
	}
}
