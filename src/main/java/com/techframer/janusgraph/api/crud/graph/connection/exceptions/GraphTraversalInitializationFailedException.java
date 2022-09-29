package com.techframer.janusgraph.api.crud.graph.connection.exceptions;

/**
 * The Class GraphTraversalInitializationFailedException.
 */
public class GraphTraversalInitializationFailedException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -7866616924378144580L;

	/** The gui message. */
	private String guiMessage;

	/**
	 * Gets the gui message.
	 *
	 * @return the gui message
	 */
	public String getGuiMessage() {
		return this.guiMessage;
	}

	/**
	 * Sets the gui message.
	 *
	 * @param message
	 *            the new gui message
	 */
	public void setGuiMessage(String message) {
		this.guiMessage = "{\"errorMsg\": \"" + message + "\"}";
	}

	/**
	 * Instantiates a new graph traversal initialization failed exception.
	 */
	public GraphTraversalInitializationFailedException() {
	}

	/**
	 * Instantiates a new graph traversal initialization failed exception.
	 *
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public GraphTraversalInitializationFailedException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new graph traversal initialization failed exception.
	 *
	 * @param cause
	 *            the cause
	 */
	public GraphTraversalInitializationFailedException(Throwable cause) {
		super(cause.getMessage(), cause);
	}

	/**
	 * Instantiates a new graph traversal initialization failed exception.
	 *
	 * @param message
	 *            the message
	 */
	public GraphTraversalInitializationFailedException(String message) {
		super(message);
		this.setGuiMessage(message);
	}

	/**
	 * Instantiates a new graph traversal initialization failed exception.
	 *
	 * @param message
	 *            the message
	 * @param guiMessage
	 *            the gui message
	 */
	public GraphTraversalInitializationFailedException(String message, String guiMessage) {
		super(message);
		this.setGuiMessage(guiMessage);
	}

}
