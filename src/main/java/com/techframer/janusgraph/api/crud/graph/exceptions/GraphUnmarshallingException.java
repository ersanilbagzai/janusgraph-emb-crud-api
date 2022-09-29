
package com.techframer.janusgraph.api.crud.graph.exceptions;

/**
 * The Class GraphUnmarshallingException.
 */
public final class GraphUnmarshallingException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8162385108397238865L;

	/**
	 * Instantiates a new graph unmarshalling exception.
	 */
	public GraphUnmarshallingException() {
	}

	/**
	 * Instantiates a new graph unmarshalling exception.
	 *
	 * @param message
	 *            the message
	 */
	public GraphUnmarshallingException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new graph unmarshalling exception.
	 *
	 * @param cause
	 *            the cause
	 */
	public GraphUnmarshallingException(Throwable cause) {
		super(cause);
	}

	/**
	 * Instantiates a new graph unmarshalling exception.
	 *
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public GraphUnmarshallingException(String message, Throwable cause) {
		super(message, cause);
	}
}
