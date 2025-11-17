package com.monexel.expensetracker.exception;

/**
 * Represents a standard API response structure for the expense tracker application.
 * This class is used to send a message and status back to the client after an API operation.
 *
 * <p>Fields:</p>
 * <ul>
 *   <li><b>message</b> - A descriptive message about the API operation result.</li>
 *   <li><b>status</b> - A boolean indicating success (true) or failure (false).</li>
 * </ul>
 *
 * <p>Usage:</p>
 * <ul>
 *   <li>Used in controllers to return a consistent response format for success or error messages.</li>
 * </ul>
 *
 * Constructors:
 * <ul>
 *   <li>{@link #APIResponse()} - Default constructor.</li>
 *   <li>{@link #APIResponse(String, boolean)} - Creates an APIResponse with a message and status.</li>
 * </ul>
 *
 * Methods:
 * <ul>
 *   <li>{@link #getMessage()} - Returns the response message.</li>
 *   <li>{@link #setMessage(String)} - Sets the response message.</li>
 *   <li>{@link #isStatus()} - Returns the status of the response.</li>
 *   <li>{@link #setStatus(boolean)} - Sets the status of the response.</li>
 *   <li>{@link #toString()} - Returns a string representation of the APIResponse.</li>
 * </ul>
 *
 * @author Surya Narayanan G
 * @version 1.0
 */

public class APIResponse {

	private String message;
	private boolean status;

	public APIResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public APIResponse(String message, boolean status) {
		super();
		this.message = message;
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "APIResponse [message=" + message + ", status=" + status + "]";
	}

}
