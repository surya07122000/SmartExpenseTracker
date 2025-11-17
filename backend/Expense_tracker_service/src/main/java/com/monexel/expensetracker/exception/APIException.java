package com.monexel.expensetracker.exception;


/**
 * Custom exception class for handling API-related errors in the expense tracker application.
 * This exception extends {@link RuntimeException}, allowing it to be thrown without
 * requiring explicit handling.
 *
 * <p>Usage:</p>
 * <ul>
 *   <li>Thrown when an API operation fails due to invalid input, business logic errors,
 *       or unexpected conditions.</li>
 *   <li>Can be used in service or controller layers to propagate meaningful error messages
 *       to the client.</li>
 * </ul>
 *
 * Constructors:
 * <ul>
 *   <li>{@link #APIException()} - Creates a new instance without a message.</li>
 *   <li>{@link #APIException(String)} - Creates a new instance with a custom error message.</li>
 * </ul>
 *
 * @author Surya Narayanan G
 * @version 1.0
 */


public class APIException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public APIException() {
		super();

	}

	public APIException(String message) {
		super(message);

	}

}
