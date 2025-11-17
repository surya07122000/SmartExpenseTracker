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

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {


	@Autowired
    private ExpenseService expenseService;

    @PostMapping("/addExpense")
    public ResponseEntity<ExpenseResponse> addExpense(@RequestBody @Valid ExpenseRequest request) {
        return new ResponseEntity<>(expenseService.addExpense(request), HttpStatus.CREATED);
    }

    @PutMapping("/updateExpense/{id}")
    public ResponseEntity<ExpenseResponse> updateExpense(@PathVariable Long id, @RequestBody @Valid ExpenseRequest request) {
        return ResponseEntity.ok(expenseService.updateExpense(id, request));
    }

    @DeleteMapping("/deleteExpense/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getExpenseById/{id}")
    public ResponseEntity<ExpenseResponse> getExpenseById(@PathVariable Long id) {
        return ResponseEntity.ok(expenseService.getExpenseById(id));
    }

    @GetMapping("/getAllExpensesByUser/{userId}")
    public ResponseEntity<List<ExpenseResponse>> getAllExpensesByUser(@PathVariable Long userId,
    		@RequestParam(required = false) LocalDate startDate,
    		@RequestParam(required = false) LocalDate endDate) {
        return ResponseEntity.ok(expenseService.getAllExpensesByUser(userId, startDate, endDate));
    }

}
