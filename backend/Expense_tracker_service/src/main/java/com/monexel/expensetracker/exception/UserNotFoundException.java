package com.monexel.expensetracker.exception;


/**
 * Exception thrown when a user cannot be found in the system.
 *
 * <p>This is a custom runtime exception used in the Expense Tracker application
 * to indicate that a user resource does not exist based on the provided identifier(s).
 * It supports multiple constructors for flexibility in specifying the missing user details.</p>
 *
 * <h2>Usage Example:</h2>
 * <pre>
 * Optional<User> user = userRepository.findById(userId);
 * if (!user.isPresent()) {
 *     throw new UserNotFoundException("User", "id", userId);
 * }
 * </pre>
 *
 * <h2>Constructors:</h2>
 * <ul>
 *   <li>{@link #UserNotFoundException()} - Default constructor.</li>
 *   <li>{@link #UserNotFoundException(String, String, String)} - For string-based identifiers.</li>
 *   <li>{@link #UserNotFoundException(String, String, Long)} - For numeric identifiers with field name.</li>
 *   <li>{@link #UserNotFoundException(String, Long)} - For numeric identifiers without field name.</li>
 * </ul>
 *
 * @author Surya Narayanan G
 * @version 1.0
 */

public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	String resourceName;
	String field;
	String fieldName;
	Long fieldId;

	public UserNotFoundException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserNotFoundException(String resourceName, String field, String fieldName) {
		super(String.format("%s not found with %s : %s", resourceName, field, fieldName));

		this.resourceName = resourceName;
		this.field = field;
		this.fieldName = fieldName;
	}

	public UserNotFoundException(String resourceName, String field, Long fieldId) {
		super(String.format("%s not found with %s : %d", resourceName, field, fieldId));
		this.resourceName = resourceName;
		this.field = field;
		this.fieldId = fieldId;
	}

	public UserNotFoundException(String resourceName, Long fieldId) {
		super(String.format("%s not found with ID : %d ", resourceName, fieldId));
		this.resourceName = resourceName;
		this.fieldId = fieldId;

	}
}
