package com.monexel.expensetracker.exception;

/**
 * Exception thrown when an attempt is made to create a resource that already
 * exists.
 *
 * <p>
 * This is a custom runtime exception used in the Expense Tracker application to
 * indicate that a resource (such as a user, category, or transaction) cannot be
 * created because it already exists in the system.
 * </p>
 *
 * <h2>Usage Example:</h2>
 * 
 * <pre>
 * if (repository.existsByEmail(email)) {
 * 	throw new ResourceAlreadyExistsException("User", "email", email);
 * }
 * </pre>
 *
 * <h2>Constructors:</h2>
 * <ul>
 * <li>{@link #ResourceAlreadyExistsException()} - Default constructor.</li>
 * <li>{@link #ResourceAlreadyExistsException(String, String, String)} - For
 * string-based identifiers.</li>
 * <li>{@link #ResourceAlreadyExistsException(String, String, Long)} - For
 * numeric identifiers.</li>
 * <li>{@link #ResourceAlreadyExistsException(String, String)} - For generic
 * resource and field names.</li>
 * </ul>
 *
 * @author Surya Narayanan G
 * @version 1.0
 */

public class ResourceAlreadyExistsException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	String resourceName;
	String field;
	String fieldName;
	Long fieldId;

	public ResourceAlreadyExistsException() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructs a new exception with resource name, field name, and string value.
	 *
	 * @param resourceName the name of the resource
	 * @param field        the field causing the conflict
	 * @param fieldName    the string value of the field
	 */

	public ResourceAlreadyExistsException(String resourceName, String field, String fieldName) {
		super(String.format("%s already exists with %s : %s", resourceName, field, fieldName));

		this.resourceName = resourceName;
		this.field = field;
		this.fieldName = fieldName;
	}

	public ResourceAlreadyExistsException(String resourceName, String field, Long fieldId) {
		super(String.format("%s already exists with %s : %d", resourceName, field, fieldId));
		this.resourceName = resourceName;
		this.field = field;
		this.fieldId = fieldId;
	}

	public ResourceAlreadyExistsException(String resourceName, String field) {
		super(String.format("%s already exists %s ", resourceName, field));
		this.resourceName = resourceName;
		this.field = field;

	}
}
