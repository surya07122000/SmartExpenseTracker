package com.monexel.expensetracker.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MyGlobalExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<APIResponse> myResourceNotFoundException(ResourceNotFoundException ex){
		String message = ex.getMessage();
		APIResponse apiResponse = new APIResponse(message,false);
		return new ResponseEntity<APIResponse>(apiResponse,HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(APIException.class)
	public ResponseEntity<APIResponse> myAPIException(APIException ex){
		String message = ex.getMessage();
		APIResponse apiResponse = new APIResponse(message,false);
		return new ResponseEntity<APIResponse>(apiResponse,HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(ResourceAlreadyExistsException.class)
	public ResponseEntity<APIResponse> myResourceAlreadyExistsException(ResourceAlreadyExistsException ex){
		String message = ex.getMessage();
		APIResponse apiResponse = new APIResponse(message,false);
		return new ResponseEntity<APIResponse>(apiResponse,HttpStatus.CONFLICT);
		
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<APIResponse> myUserNotFoundException(UserNotFoundException ex){
		String message = ex.getMessage();
		APIResponse apiResponse = new APIResponse(message,false);
		return new ResponseEntity<APIResponse>(apiResponse,HttpStatus.NOT_FOUND);
		
	}

	 @ExceptionHandler(InsufficientFundsException.class)
	    public ResponseEntity<APIResponse> myInsufficientFundsException(InsufficientFundsException ex){
	        String message = ex.getMessage();
	        APIResponse apiResponse = new APIResponse(message,false);
	        return new ResponseEntity<>(apiResponse,HttpStatus.BAD_REQUEST);
	    }
	 

	 @ExceptionHandler(MethodArgumentNotValidException.class)
	    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
	        Map<String, String> errors = new HashMap<>();
	        ex.getBindingResult().getFieldErrors().forEach(error -> {
	            errors.put(error.getField(), error.getDefaultMessage());
	        });
	        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	    }


	
}
