package com.monexel.expensetracker.exception;


/**
 * Custom exception to indicate that an operation cannot be completed
 * due to insufficient funds in the account or wallet.
 *
 * <p>This exception is typically thrown when a withdrawal or payment
 * exceeds the available balance.</p>
 *
 * <p>Example usage:</p>
 * <pre>
 * if (balance < withdrawalAmount) {
 *     throw new InsufficientFundsException("Insufficient balance for withdrawal");
 * }
 * </pre>
 *
 * @author Surya Narayanan G
 * @version 1.0
 */


public class InsufficientFundsException extends RuntimeException{

	
	private static final long serialVersionUID = 1L;

		public InsufficientFundsException(String message)  {
        super(message);
		}


}
