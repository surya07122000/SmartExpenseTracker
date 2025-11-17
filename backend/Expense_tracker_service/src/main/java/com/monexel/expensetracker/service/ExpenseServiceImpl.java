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

/**
 * Service implementation for managing expenses in the Expense Tracker
 * application.
 *
 * <p>
 * This class provides business logic for adding, updating, deleting, and
 * retrieving expense details. It validates user and category associations,
 * ensures sufficient funds before adding an expense, and interacts with
 * repositories for persistence.
 * </p>
 *
 * <h2>Responsibilities:</h2>
 * <ul>
 * <li>Add new expenses after validating user, category, and available
 * balance.</li>
 * <li>Update existing expense details.</li>
 * <li>Delete expenses by ID.</li>
 * <li>Retrieve expense details by ID or fetch all expenses for a user within an
 * optional date range.</li>
 * </ul>
 *
 * <h2>Validation Rules:</h2>
 * <ul>
 * <li>User must exist in the system.</li>
 * <li>Category must exist and belong to the same user (if custom).</li>
 * <li>Net balance (income + borrowed - expenses) must be sufficient for the new
 * expense.</li>
 * </ul>
 *
 * <h2>Exception Handling:</h2>
 * <ul>
 * <li>{@link ResourceNotFoundException} - When user, category, or expense is
 * not found.</li>
 * <li>{@link APIException} - When category belongs to another user.</li>
 * <li>{@link InsufficientFundsException} - When funds are insufficient for the
 * expense.</li>
 * </ul>
 *
 * <h2>Usage Example:</h2>
 * 
 * <pre>
 * ExpenseRequest request = new ExpenseRequest();
 * request.setTitle("Groceries");
 * request.setAmount(new BigDecimal("1500"));
 * request.setDate(LocalDate.now());
 * request.setUserId(1L);
 * request.setCategoryId(2L);
 *
 * ExpenseResponse response = expenseService.addExpense(request);
 * </pre>
 *
 * @author Surya Narayanan G
 * @version 1.0
 */

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

	/**
	 * Adds a new expense after validating user, category, and available funds.
	 *
	 * @param request the {@link ExpenseRequest} containing expense details
	 * @return a {@link ExpenseResponse} representing the saved expense
	 * @throws ResourceNotFoundException  if the user or category does not exist
	 * @throws APIException               if the category belongs to another user
	 * @throws InsufficientFundsException if the user does not have enough funds
	 */

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

	/**
	 * Updates an existing expense.
	 *
	 * @param id      the ID of the expense to update
	 * @param request the {@link ExpenseRequest} containing updated details
	 * @return a {@link ExpenseResponse} representing the updated expense
	 * @throws ResourceNotFoundException if the expense or category does not exist
	 */

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

	/**
	 * Deletes an expense by ID.
	 *
	 * @param id the ID of the expense
	 * @throws ResourceNotFoundException if the expense does not exist
	 */

	@Override
	public void deleteExpense(Long id) {
		if (!expenseRepository.existsById(id)) {
			throw new ResourceNotFoundException("Expense", "id", id);
		}
		expenseRepository.deleteById(id);
	}

	/**
	 * Retrieves an expense by ID.
	 *
	 * @param id the ID of the expense
	 * @return a {@link ExpenseResponse} representing the expense
	 * @throws ResourceNotFoundException if the expense does not exist
	 */

	@Override
	public ExpenseResponse getExpenseById(Long id) {
		Expense expense = expenseRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Expense", "id", id));
		return mapToResponse(expense);
	}

	/**
	 * Retrieves all expenses for a user, optionally filtered by a date range.
	 *
	 * @param userId    the ID of the user
	 * @param startDate the start date of the range (optional)
	 * @param endDate   the end date of the range (optional)
	 * @return a list of {@link ExpenseResponse} records
	 */

	@Override
	public List<ExpenseResponse> getAllExpensesByUser(Long userId, LocalDate startDate, LocalDate endDate) {
		if (startDate != null && endDate != null) {
			return expenseRepository.findByUserIdAndDateBetween(userId, startDate, endDate).stream()
					.map(this::mapToResponse).collect(Collectors.toList());
		}
		return expenseRepository.findByUserId(userId).stream().map(this::mapToResponse).collect(Collectors.toList());
	}

	/**
	 * Maps an {@link Expense} entity to an {@link ExpenseResponse}.
	 *
	 * @param expense the entity to map
	 * @return the mapped response object
	 */

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
