package com.monexel.expensetracker.exception;


/**
 * Exception thrown when a requested resource cannot be found.
 *
 * <p>This is a custom runtime exception used in the Expense Tracker application
 * to indicate that a resource (such as a user, category, or transaction) does not exist
 * in the system.</p>
 *
 * <h2>Usage Example:</h2>
 * <pre>
 * Optional<User> user = userRepository.findById(id);
 * if (!user.isPresent()) {
 *     throw new ResourceNotFoundException("User", "id", id);
 * }
 * </pre>
 *
 * <h2>Constructors:</h2>
 * <ul>
 *   <li>{@link #ResourceNotFoundException(String)} - Accepts a custom message.</li>
 *   <li>{@link #ResourceNotFoundException(String, String, Object)} - Accepts resource name, field name, and field value.</li>
 * </ul>
 *
 * @author Surya Narayanan G
 * @version 1.0
 */


public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
    }
}
