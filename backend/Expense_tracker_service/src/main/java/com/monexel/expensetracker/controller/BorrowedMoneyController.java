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

import com.monexel.expensetracker.request.BorrowedMoneyRequest;
import com.monexel.expensetracker.response.BorrowedMoneyResponse;
import com.monexel.expensetracker.service.BorrowedMoneyService;

import jakarta.validation.Valid;

/**
 * Controller for managing borrowed money operations. Provides endpoints to add,
 * update, delete, and retrieve borrowed money records.
 * @author Surya Narayanan G
 * @version 1.0

 */
@RestController
@RequestMapping("/api/borrowed-money")
public class BorrowedMoneyController {

	@Autowired
	private BorrowedMoneyService borrowedMoneyService;

	/**
	 * Adds a new borrowed money record.
	 *
	 * @param request the {@link BorrowedMoneyRequest} containing details of
	 *                borrowed money
	 * @return ResponseEntity containing the created {@link BorrowedMoneyResponse}
	 *         and HTTP status 201 (Created)
	 */

	@PostMapping("/addBorrowedMoney")
	public ResponseEntity<BorrowedMoneyResponse> addBorrowedMoney(@RequestBody @Valid BorrowedMoneyRequest request) {
		return new ResponseEntity<>(borrowedMoneyService.addBorrowedMoney(request), HttpStatus.CREATED);
	}

	/**
	 * Updates an existing borrowed money record by ID.
	 *
	 * @param id      the ID of the borrowed money record to update
	 * @param request the {@link BorrowedMoneyRequest} with updated details
	 * @return ResponseEntity containing the updated {@link BorrowedMoneyResponse}
	 * @throws ResourceNotFoundException if the record with given ID does not exist
	 */

	@PutMapping("/updateBorrowedMoney/{id}")
	public ResponseEntity<BorrowedMoneyResponse> updateBorrowedMoney(@PathVariable Long id,
			@RequestBody @Valid BorrowedMoneyRequest request) {
		return ResponseEntity.ok(borrowedMoneyService.updateBorrowedMoney(id, request));
	}

	/**
	 * Deletes a borrowed money record by ID.
	 *
	 * @param id the ID of the borrowed money record to delete
	 * @return ResponseEntity with HTTP status 204 (No Content)
	 * @throws ResourceNotFoundException if the record with given ID does not exist
	 */

	@DeleteMapping("/deleteBorrowedMoney/{id}")
	public ResponseEntity<Void> deleteBorrowedMoney(@PathVariable Long id) {
		borrowedMoneyService.deleteBorrowedMoney(id);
		return ResponseEntity.noContent().build();
	}

	/**
	 * Retrieves a borrowed money record by ID.
	 *
	 * @param id the ID of the borrowed money record
	 * @return ResponseEntity containing the {@link BorrowedMoneyResponse}
	 * @throws ResourceNotFoundException if the record with given ID does not exist
	 */

	@GetMapping("/getBorrowedMoneyById/{id}")
	public ResponseEntity<BorrowedMoneyResponse> getBorrowedMoneyById(@PathVariable Long id) {
		return ResponseEntity.ok(borrowedMoneyService.getBorrowedMoneyById(id));
	}

	/**
	 * Retrieves all borrowed money records for a user, optionally filtered by date
	 * range.
	 *
	 * @param userId    the ID of the user
	 * @param startDate optional start date for filtering
	 * @param endDate   optional end date for filtering
	 * @return ResponseEntity containing a list of {@link BorrowedMoneyResponse}
	 */

	@GetMapping("/getAllBorrowedMoneyByUser/{userId}")
	public ResponseEntity<List<BorrowedMoneyResponse>> getAllBorrowedMoneyByUser(@PathVariable Long userId,
			@RequestParam(required = false) LocalDate startDate, @RequestParam(required = false) LocalDate endDate) {
		return ResponseEntity.ok(borrowedMoneyService.getAllBorrowedMoneyByUser(userId, startDate, endDate));
	}

}
