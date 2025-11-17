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

import com.monexel.expensetracker.request.IncomeRequest;
import com.monexel.expensetracker.response.IncomeResponse;
import com.monexel.expensetracker.service.IncomeService;

import jakarta.validation.Valid;

/**
 * REST controller for managing income operations. Provides endpoints to add,
 * update, delete, and retrieve income records for users, with optional date
 * range filtering.
 * @author Surya Narayanan G
 * @version 1.0
 */

@RestController
@RequestMapping("/api/income")
public class IncomeController {

	@Autowired
	private IncomeService incomeService;

	/**
	 * Adds a new income record.
	 *
	 * @param request the {@link IncomeRequest} containing income details
	 * @return ResponseEntity containing the created {@link IncomeResponse} and HTTP
	 *         status 201 (Created)
	 *
	 *         Example:
	 * 
	 *         <pre>
	 *         POST / api / income / addIncome
	 *         </pre>
	 */

	@PostMapping("/addIncome")
	public ResponseEntity<IncomeResponse> addIncome(@RequestBody @Valid IncomeRequest request) {
		return new ResponseEntity<>(incomeService.addIncome(request), HttpStatus.CREATED);
	}

	/**
	 * Updates an existing income record by ID.
	 *
	 * @param id      the ID of the income record to update
	 * @param request the {@link IncomeRequest} with updated details
	 * @return ResponseEntity containing the updated {@link IncomeResponse}
	 * @throws ResourceNotFoundException if the income record with given ID does not
	 *                                   exist
	 *
	 *                                   Example:
	 * 
	 *                                   <pre>
	 *                                   PUT / api / income / updateIncome / 1
	 *                                   </pre>
	 */

	@PutMapping("/updateIncome/{id}")
	public ResponseEntity<IncomeResponse> updateIncome(@PathVariable Long id,
			@RequestBody @Valid IncomeRequest request) {
		return ResponseEntity.ok(incomeService.updateIncome(id, request));
	}

	/**
	 * Deletes an income record by ID.
	 *
	 * @param id the ID of the income record to delete
	 * @return ResponseEntity with HTTP status 204 (No Content)
	 * @throws ResourceNotFoundException if the income record with given ID does not
	 *                                   exist
	 *
	 *                                   Example:
	 * 
	 *                                   <pre>
	 *                                   DELETE / api / income / deleteIncome / 1
	 *                                   </pre>
	 */

	@DeleteMapping("/deleteIncome/{id}")
	public ResponseEntity<Void> deleteIncome(@PathVariable Long id) {
		incomeService.deleteIncome(id);
		return ResponseEntity.noContent().build();
	}

	/**
	 * Retrieves an income record by ID.
	 *
	 * @param id the ID of the income record
	 * @return ResponseEntity containing the {@link IncomeResponse}
	 * @throws ResourceNotFoundException if the income record with given ID does not
	 *                                   exist
	 *
	 *                                   Example:
	 * 
	 *                                   <pre>
	 *                                   GET / api / income / getIncomeById / 1
	 *                                   </pre>
	 */

	@GetMapping("getIncomeById/{id}")
	public ResponseEntity<IncomeResponse> getIncomeById(@PathVariable Long id) {
		return ResponseEntity.ok(incomeService.getIncomeById(id));
	}

	/**
	 * Retrieves all income records for a user, optionally filtered by date range.
	 *
	 * @param userId    the ID of the user
	 * @param startDate optional start date for filtering
	 * @param endDate   optional end date for filtering
	 * @return ResponseEntity containing a list of {@link IncomeResponse}
	 *
	 *         Example:
	 * 
	 *         <pre>
	 * GET /api/income/getAllIncomeByUser/1?startDate=2025-11-01&endDate=2025-11-30
	 *         </pre>
	 */

	@GetMapping("/getAllIncomeByUser/{userId}")
	public ResponseEntity<List<IncomeResponse>> getAllIncomesByUser(@PathVariable Long userId,
			@RequestParam(required = false) LocalDate startDate, @RequestParam(required = false) LocalDate endDate) {
		return ResponseEntity.ok(incomeService.getAllIncomesByUser(userId, startDate, endDate));
	}

}
