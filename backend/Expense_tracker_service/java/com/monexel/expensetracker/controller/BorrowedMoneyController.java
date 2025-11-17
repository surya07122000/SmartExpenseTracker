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

@RestController
@RequestMapping("/api/borrowed-money")
public class BorrowedMoneyController {

	@Autowired
	private BorrowedMoneyService borrowedMoneyService;

	@PostMapping("/addBorrowedMoney")
	public ResponseEntity<BorrowedMoneyResponse> addBorrowedMoney(@RequestBody @Valid BorrowedMoneyRequest request) {
		return new ResponseEntity<>(borrowedMoneyService.addBorrowedMoney(request), HttpStatus.CREATED);
	}

	@PutMapping("/updateBorrowedMoney/{id}")
	public ResponseEntity<BorrowedMoneyResponse> updateBorrowedMoney(@PathVariable Long id,
			@RequestBody @Valid BorrowedMoneyRequest request) {
		return ResponseEntity.ok(borrowedMoneyService.updateBorrowedMoney(id, request));
	}

	@DeleteMapping("/deleteBorrowedMoney/{id}")
	public ResponseEntity<Void> deleteBorrowedMoney(@PathVariable Long id) {
		borrowedMoneyService.deleteBorrowedMoney(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/getBorrowedMoneyById/{id}")
	public ResponseEntity<BorrowedMoneyResponse> getBorrowedMoneyById(@PathVariable Long id) {
		return ResponseEntity.ok(borrowedMoneyService.getBorrowedMoneyById(id));
	}

	@GetMapping("/getAllBorrowedMoneyByUser/{userId}")
	public ResponseEntity<List<BorrowedMoneyResponse>> getAllBorrowedMoneyByUser(@PathVariable Long userId,
    		@RequestParam(required = false) LocalDate startDate,
    		@RequestParam(required = false) LocalDate endDate) {
		return ResponseEntity.ok(borrowedMoneyService.getAllBorrowedMoneyByUser(userId, startDate, endDate));
	}

}
