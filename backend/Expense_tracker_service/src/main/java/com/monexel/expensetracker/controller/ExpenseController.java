package com.monexel.expensetracker.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.monexel.expensetracker.request.ExpenseRequest;
import com.monexel.expensetracker.response.ExpenseResponse;
import com.monexel.expensetracker.service.ExpenseService;

import jakarta.validation.Valid;

/**
 * REST controller for managing expense operations. Provides endpoints to add,
 * update, delete, and retrieve expenses for users, with optional date range
 * filtering.
 * @author Surya Narayanan G
 * @version 1.0
 */

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

	@Autowired
	private ExpenseService expenseService;

	/**
	 * Adds a new expense.
	 *
	 * @param request the {@link ExpenseRequest} containing expense details
	 * @return ResponseEntity containing the created {@link ExpenseResponse} and
	 *         HTTP status 201 (Created)
	 *
	 *         Example:
	 * 
	 *         <pre>
	 *         POST / api / expenses / addExpense
	 *         </pre>
	 */

	@PostMapping("/addExpense")
	public ResponseEntity<ExpenseResponse> addExpense(@RequestBody @Valid ExpenseRequest request) {
		return new ResponseEntity<>(expenseService.addExpense(request), HttpStatus.CREATED);
	}

	/**
	 * Updates an existing expense by ID.
	 *
	 * @param id      the ID of the expense to update
	 * @param request the {@link ExpenseRequest} with updated details
	 * @return ResponseEntity containing the updated {@link ExpenseResponse}
	 * @throws ResourceNotFoundException if the expense with given ID does not exist
	 *
	 *                                   Example:
	 * 
	 *                                   <pre>
	 *                                   PUT / api / expenses / updateExpense / 1
	 *                                   </pre>
	 */

	@PutMapping("/updateExpense/{id}")
	public ResponseEntity<ExpenseResponse> updateExpense(@PathVariable Long id,
			@RequestBody @Valid ExpenseRequest request) {
		return ResponseEntity.ok(expenseService.updateExpense(id, request));
	}

	/**
	 * Updates an existing expense by ID.
	 *
	 * @param id      the ID of the expense to update
	 * @param request the {@link ExpenseRequest} with updated details
	 * @return ResponseEntity containing the updated {@link ExpenseResponse}
	 * @throws ResourceNotFoundException if the expense with given ID does not exist
	 *
	 *                                   Example:
	 * 
	 *                                   <pre>
	 *                                   PUT / api / expenses / updateExpense / 1
	 *                                   </pre>
	 */

	@DeleteMapping("/deleteExpense/{id}")
	public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
		expenseService.deleteExpense(id);
		return ResponseEntity.noContent().build();
	}

	/**
	 * Retrieves an expense by ID.
	 *
	 * @param id the ID of the expense
	 * @return ResponseEntity containing the {@link ExpenseResponse}
	 * @throws ResourceNotFoundException if the expense with given ID does not exist
	 *
	 *                                   Example:
	 * 
	 *                                   <pre>
	 *                                   GET / api / expenses / getExpenseById / 1
	 *                                   </pre>
	 */

	@GetMapping("/getExpenseById/{id}")
	public ResponseEntity<ExpenseResponse> getExpenseById(@PathVariable Long id) {
		return ResponseEntity.ok(expenseService.getExpenseById(id));
	}

	/**
	 * Retrieves all expenses for a user, optionally filtered by date range.
	 *
	 * @param userId    the ID of the user
	 * @param startDate optional start date for filtering
	 * @param endDate   optional end date for filtering
	 * @return ResponseEntity containing a list of {@link ExpenseResponse}
	 *
	 *         Example:
	 * 
	 *         <pre>
	 * GET /api/expenses/getAllExpensesByUser/1?startDate=2025-11-01&endDate=2025-11-30
	 *         </pre>
	 */

	@GetMapping("/getAllExpensesByUser/{userId}")
	public ResponseEntity<List<ExpenseResponse>> getAllExpensesByUser(@PathVariable Long userId,
			@RequestParam(required = false) LocalDate startDate, @RequestParam(required = false) LocalDate endDate) {
		return ResponseEntity.ok(expenseService.getAllExpensesByUser(userId, startDate, endDate));
	}

}
