package com.monexel.expensetracker.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monexel.expensetracker.entity.Income;
import com.monexel.expensetracker.entity.User;
import com.monexel.expensetracker.exception.ResourceNotFoundException;
import com.monexel.expensetracker.repository.IncomeRepository;
import com.monexel.expensetracker.repository.UserRepository;
import com.monexel.expensetracker.request.IncomeRequest;
import com.monexel.expensetracker.response.IncomeResponse;

/**
 * Service implementation for managing income records in the Expense Tracker
 * application.
 *
 * <p>
 * This class provides business logic for adding, updating, deleting, and
 * retrieving income details. It interacts with {@link IncomeRepository} for
 * persistence and {@link UserRepository} for validating user existence.
 * </p>
 *
 * <h2>Responsibilities:</h2>
 * <ul>
 * <li>Add new income records for a user.</li>
 * <li>Update existing income details.</li>
 * <li>Delete income records by ID.</li>
 * <li>Retrieve income details by ID or fetch all incomes for a user within an
 * optional date range.</li>
 * </ul>
 *
 * <h2>Exception Handling:</h2>
 * <ul>
 * <li>{@link ResourceNotFoundException} is thrown when a user or income record
 * is not found.</li>
 * </ul>
 *
 * <h2>Usage Example:</h2>
 * 
 * <pre>
 * IncomeRequest request = new IncomeRequest();
 * request.setSource("Salary");
 * request.setAmount(new BigDecimal("50000"));
 * request.setDate(LocalDate.now());
 * request.setDescription("Monthly salary");
 * request.setUserId(1L);
 *
 * IncomeResponse response = incomeService.addIncome(request);
 * </pre>
 *
 * @author Surya Narayanan G
 * @version 1.0
 */

@Service
public class IncomeServiceImpl implements IncomeService {

	@Autowired
	private IncomeRepository incomeRepository;

	@Autowired
	private UserRepository userRepository;

	/**
	 * Adds a new income record for a user.
	 *
	 * @param request the {@link IncomeRequest} containing income details
	 * @return a {@link IncomeResponse} representing the saved income
	 * @throws ResourceNotFoundException if the user does not exist
	 */

	@Override
	public IncomeResponse addIncome(IncomeRequest request) {
		User user = userRepository.findById(request.getUserId())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", request.getUserId()));

		Income income = new Income();
		income.setSource(request.getSource());
		income.setAmount(request.getAmount());
		income.setDate(request.getDate());
		income.setDescription(request.getDescription());
		income.setUser(user);

		Income savedIncome = incomeRepository.save(income);
		return mapToResponse(savedIncome);
	}

	/**
	 * Updates an existing income record.
	 *
	 * @param id      the ID of the income record to update
	 * @param request the {@link IncomeRequest} containing updated details
	 * @return a {@link IncomeResponse} representing the updated income
	 * @throws ResourceNotFoundException if the income record does not exist
	 */

	@Override
	public IncomeResponse updateIncome(Long id, IncomeRequest request) {
		Income income = incomeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Income", "id", id));

		income.setSource(request.getSource());
		income.setAmount(request.getAmount());
		income.setDate(request.getDate());
		income.setDescription(request.getDescription());
		Income updatedIncome = incomeRepository.save(income);
		return mapToResponse(updatedIncome);
	}

	/**
	 * Deletes an income record by ID.
	 *
	 * @param id the ID of the income record
	 * @throws ResourceNotFoundException if the income record does not exist
	 */

	@Override
	public void deleteIncome(Long id) {
		if (!incomeRepository.existsById(id)) {
			throw new ResourceNotFoundException("Income", "id", id);
		}
		incomeRepository.deleteById(id);
	}

	/**
	 * Retrieves an income record by ID.
	 *
	 * @param id the ID of the income record
	 * @return a {@link IncomeResponse} representing the income
	 * @throws ResourceNotFoundException if the income record does not exist
	 */

	@Override
	public IncomeResponse getIncomeById(Long id) {
		Income income = incomeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Income", "id", id));
		return mapToResponse(income);
	}

	/**
	 * Retrieves all income records for a user, optionally filtered by a date range.
	 *
	 * @param userId    the ID of the user
	 * @param startDate the start date of the range (optional)
	 * @param endDate   the end date of the range (optional)
	 * @return a list of {@link IncomeResponse} records
	 */

	@Override
	public List<IncomeResponse> getAllIncomesByUser(Long userId, LocalDate startDate, LocalDate endDate) {

		if (startDate != null && endDate != null) {
			return incomeRepository.findByUserIdAndDateBetween(userId, startDate, endDate).stream()
					.map(this::mapToResponse).collect(Collectors.toList());
		}

		return incomeRepository.findByUserId(userId).stream().map(this::mapToResponse).collect(Collectors.toList());
	}

	/**
	 * Maps an {@link Income} entity to an {@link IncomeResponse}.
	 *
	 * @param income the entity to map
	 * @return the mapped response object
	 */

	private IncomeResponse mapToResponse(Income income) {
		IncomeResponse response = new IncomeResponse();
		response.setId(income.getId());
		response.setSource(income.getSource());
		response.setAmount(income.getAmount());
		response.setDate(income.getDate());
		response.setUserId(income.getUser().getId());
		response.setDescription(income.getDescription());
		return response;
	}

}
