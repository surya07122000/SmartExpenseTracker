package com.monexel.expensetracker.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler for the Expense Tracker application.
 * 
 * <p>
 * This class uses {@code @RestControllerAdvice} to handle exceptions globally
 * across all controllers. It provides custom responses for various
 * application-specific exceptions and validation errors.
 * </p>
 *
 * <h2>Handled Exceptions:</h2>
 * <ul>
 * <li>{@link ResourceNotFoundException} - When a requested resource is not
 * found.</li>
 * <li>{@link APIException} - For generic API-related errors.</li>
 * <li>{@link ResourceAlreadyExistsException} - When attempting to create a
 * resource that already exists.</li>
 * <li>{@link UserNotFoundException} - When a user is not found in the
 * system.</li>
 * <li>{@link InsufficientFundsException} - When a transaction fails due to
 * insufficient funds.</li>
 * <li>{@link MethodArgumentNotValidException} - For validation errors on
 * request payloads.</li>
 * </ul>
 *
 * <h2>Response Structure:</h2>
 * <p>
 * Most handlers return an {@link APIResponse} object with a message and success
 * flag, wrapped in a {@link ResponseEntity} with an appropriate HTTP status
 * code.
 * </p>
 *
 * <h2>Validation Errors:</h2>
 * <p>
 * Validation errors are returned as a {@code Map<String, String>} where keys
 * are field names and values are error messages.
 * </p>
 *
 * @author Surya Narayanan G
 * @version 1.0
 */

@RestControllerAdvice
public class MyGlobalExceptionHandler {

	/**
	 * Handles {@link ResourceNotFoundException}.
	 *
	 * @param ex the exception instance
	 * @return ResponseEntity containing APIResponse with NOT_FOUND status
	 */

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<APIResponse> myResourceNotFoundException(ResourceNotFoundException ex) {
		String message = ex.getMessage();
		APIResponse apiResponse = new APIResponse(message, false);
		return new ResponseEntity<APIResponse>(apiResponse, HttpStatus.NOT_FOUND);

	}

	/**
	 * Handles {@link APIException}.
	 *
	 * @param ex the exception instance
	 * @return ResponseEntity containing APIResponse with BAD_REQUEST status
	 */

	@ExceptionHandler(APIException.class)
	public ResponseEntity<APIResponse> myAPIException(APIException ex) {
		String message = ex.getMessage();
		APIResponse apiResponse = new APIResponse(message, false);
		return new ResponseEntity<APIResponse>(apiResponse, HttpStatus.BAD_REQUEST);

	}

	/**
	 * Handles {@link ResourceAlreadyExistsException}.
	 *
	 * @param ex the exception instance
	 * @return ResponseEntity containing APIResponse with CONFLICT status
	 */

	@ExceptionHandler(ResourceAlreadyExistsException.class)
	public ResponseEntity<APIResponse> myResourceAlreadyExistsException(ResourceAlreadyExistsException ex) {
		String message = ex.getMessage();
		APIResponse apiResponse = new APIResponse(message, false);
		return new ResponseEntity<APIResponse>(apiResponse, HttpStatus.CONFLICT);

	}

	/**
	 * Handles {@link UserNotFoundException}.
	 *
	 * @param ex the exception instance
	 * @return ResponseEntity containing APIResponse with NOT_FOUND status
	 */

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<APIResponse> myUserNotFoundException(UserNotFoundException ex) {
		String message = ex.getMessage();
		APIResponse apiResponse = new APIResponse(message, false);
		return new ResponseEntity<APIResponse>(apiResponse, HttpStatus.NOT_FOUND);

	}

	/**
	 * Handles {@link InsufficientFundsException}.
	 *
	 * @param ex the exception instance
	 * @return ResponseEntity containing APIResponse with BAD_REQUEST status
	 */

	@ExceptionHandler(InsufficientFundsException.class)
	public ResponseEntity<APIResponse> myInsufficientFundsException(InsufficientFundsException ex) {
		String message = ex.getMessage();
		APIResponse apiResponse = new APIResponse(message, false);
		return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handles validation errors thrown by {@link MethodArgumentNotValidException}.
	 *
	 * @param ex the exception instance
	 * @return ResponseEntity containing a map of field names and error messages
	 *         with BAD_REQUEST status
	 */

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getFieldErrors().forEach(error -> {
			errors.put(error.getField(), error.getDefaultMessage());
		});
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}

}
