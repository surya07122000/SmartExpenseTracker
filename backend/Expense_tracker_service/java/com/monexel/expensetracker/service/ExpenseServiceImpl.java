package com.monexel.expensetracker.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monexel.expensetracker.entity.Category;
import com.monexel.expensetracker.entity.Expense;
import com.monexel.expensetracker.entity.User;
import com.monexel.expensetracker.exception.APIException;
import com.monexel.expensetracker.exception.InsufficientFundsException;
import com.monexel.expensetracker.exception.ResourceNotFoundException;
import com.monexel.expensetracker.repository.BorrowedMoneyRepository;
import com.monexel.expensetracker.repository.CategoryRepository;
import com.monexel.expensetracker.repository.ExpenseRepository;
import com.monexel.expensetracker.repository.IncomeRepository;
import com.monexel.expensetracker.repository.UserRepository;
import com.monexel.expensetracker.request.ExpenseRequest;
import com.monexel.expensetracker.response.ExpenseResponse;

@Service
public class ExpenseServiceImpl implements ExpenseService {

	@Autowired
	private ExpenseRepository expenseRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private IncomeRepository incomeRepository;

	@Autowired
	private BorrowedMoneyRepository borrowedMoneyRepository;

	@Override
	public ExpenseResponse addExpense(ExpenseRequest request) {

		// Validate User
		User user = userRepository.findById(request.getUserId())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", request.getUserId()));

		// Validate Category
		Category category = categoryRepository.findById(request.getCategoryId())
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", request.getCategoryId()));

		if (category.getCreatedByUser() != null && !category.getCreatedByUser().getId().equals(user.getId())) {
			throw new APIException("You cannot use a category created by another user.");
		}

		// ✅ Calculate totals for validation
		BigDecimal totalIncome = incomeRepository.findByUserId(user.getId()).stream().map(i -> i.getAmount())
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		BigDecimal totalExpense = expenseRepository.findByUserId(user.getId()).stream().map(e -> e.getAmount())
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		BigDecimal totalBorrowed = borrowedMoneyRepository.findByUserId(user.getId()).stream().map(b -> b.getAmount())
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		BigDecimal netBalance = totalIncome.add(totalBorrowed).subtract(totalExpense);

		// ✅ Validation: Ensure enough funds
		if (netBalance.compareTo(request.getAmount()) < 0) {
			throw new InsufficientFundsException("Insufficient funds! Please add income first.");
		}

		// ✅ Save expense (do NOT modify income or borrowed money)
		Expense expense = new Expense();
		expense.setTitle(request.getTitle());
		expense.setAmount(request.getAmount());
		expense.setDate(request.getDate());
		expense.setCategory(category);
		expense.setUser(user);

		Expense savedExpense = expenseRepository.save(expense);
		return mapToResponse(savedExpense);

	}

	@Override
	public ExpenseResponse updateExpense(Long id, ExpenseRequest request) {
		Expense expense = expenseRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Expense", "id", id));

		Category category = categoryRepository.findById(request.getCategoryId())
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", request.getCategoryId()));

		expense.setTitle(request.getTitle());
		expense.setAmount(request.getAmount());
		expense.setDate(request.getDate());
		expense.setCategory(category);

		Expense updatedExpense = expenseRepository.save(expense);
		return mapToResponse(updatedExpense);
	}

	@Override
	public void deleteExpense(Long id) {
		if (!expenseRepository.existsById(id)) {
			throw new ResourceNotFoundException("Expense", "id", id);
		}
		expenseRepository.deleteById(id);
	}

	@Override
	public ExpenseResponse getExpenseById(Long id) {
		Expense expense = expenseRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Expense", "id", id));
		return mapToResponse(expense);
	}

	@Override
	public List<ExpenseResponse> getAllExpensesByUser(Long userId,LocalDate startDate, LocalDate endDate) {
		if (startDate != null && endDate != null) {
			return expenseRepository.findByUserIdAndDateBetween(userId, startDate, endDate).stream()
					.map(this::mapToResponse).collect(Collectors.toList());
    }
		return expenseRepository.findByUserId(userId).stream().map(this::mapToResponse).collect(Collectors.toList());
	}

	private ExpenseResponse mapToResponse(Expense expense) {
		ExpenseResponse response = new ExpenseResponse();
		response.setId(expense.getId());
		response.setTitle(expense.getTitle());
		response.setAmount(expense.getAmount());
		response.setDate(expense.getDate());
		response.setCategoryName(expense.getCategory().getName());
		response.setUserId(expense.getUser().getId());
		return response;
	}

}
