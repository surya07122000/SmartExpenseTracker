package com.monexel.expensetracker.service;

import java.time.LocalDate;
import java.util.List;

import com.monexel.expensetracker.request.ExpenseRequest;
import com.monexel.expensetracker.response.ExpenseResponse;

public interface ExpenseService {

	ExpenseResponse addExpense(ExpenseRequest request);

	ExpenseResponse updateExpense(Long id, ExpenseRequest request);

	void deleteExpense(Long id);

	ExpenseResponse getExpenseById(Long id);

	List<ExpenseResponse> getAllExpensesByUser(Long userId,LocalDate startDate, LocalDate endDate);
}
