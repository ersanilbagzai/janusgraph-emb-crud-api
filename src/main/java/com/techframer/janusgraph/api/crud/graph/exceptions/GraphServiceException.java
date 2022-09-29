
package com.techframer.janusgraph.api.crud.graph.exceptions;

import org.springframework.http.HttpStatus;

/**
 * The Class GraphServiceException.
 */
public class GraphServiceException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8162385108397238865L;

	/** The http status. */
	private HttpStatus httpStatus;

	/**
	 * Instantiates a new graph service exception.
	 */
	public GraphServiceException() {
	}

	public GraphServiceException(String message) {
		super(message);
	}
	
	/**
	 * Instantiates a new graph service exception.
	 *
	 * @param message the message
	 * @param httpStatus the http status
	 */
	public GraphServiceException(String message, HttpStatus httpStatus) {
		super(message);
		this.setHttpStatus(httpStatus);
	}

	/**
	 * Instantiates a new graph service exception.
	 *
	 * @param cause the cause
	 */
	public GraphServiceException(Throwable cause) {
		super(cause);
	}

	/**
	 * Instantiates a new graph service exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public GraphServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new graph service exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 * @param enableSuppression the enable suppression
	 * @param writableStackTrace the writable stack trace
	 */
	public GraphServiceException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * Gets the http status.
	 *
	 * @return the http status
	 */
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	/**
	 * Sets the http status.
	 *
	 * @param httpStatus the new http status
	 */
	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}
}
