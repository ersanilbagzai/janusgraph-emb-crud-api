
package com.techframer.janusgraph.api.crud.graph.exceptions;

/**
 * The Class GraphRelationshipNotExistsException.
 */
public final class GraphEdgeNotExistsException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3006050460369110202L;

	/**
	 * Instantiates a new graph relationship not exists exception.
	 */
	public GraphEdgeNotExistsException() {
	}

	/**
	 * Instantiates a new graph relationship not exists exception.
	 *
	 * @param message
	 *            the message
	 */
	public GraphEdgeNotExistsException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new graph relationship not exists exception.
	 *
	 * @param cause
	 *            the cause
	 */
	public GraphEdgeNotExistsException(Throwable cause) {
		super(cause);
	}

	/**
	 * Instantiates a new graph relationship not exists exception.
	 *
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public GraphEdgeNotExistsException(String message, Throwable cause) {
		super(message, cause);
	}
}
