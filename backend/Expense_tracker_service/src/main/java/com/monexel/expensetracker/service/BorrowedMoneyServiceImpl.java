package com.monexel.expensetracker.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monexel.expensetracker.entity.BorrowedMoney;
import com.monexel.expensetracker.entity.User;
import com.monexel.expensetracker.exception.ResourceNotFoundException;
import com.monexel.expensetracker.repository.BorrowedMoneyRepository;
import com.monexel.expensetracker.repository.UserRepository;
import com.monexel.expensetracker.request.BorrowedMoneyRequest;
import com.monexel.expensetracker.response.BorrowedMoneyResponse;

/**
 * Service implementation for managing borrowed money records in the Expense
 * Tracker application.
 *
 * <p>
 * This class provides business logic for adding, updating, deleting, and
 * retrieving borrowed money details. It interacts with
 * {@link BorrowedMoneyRepository} for persistence and {@link UserRepository}
 * for user validation.
 * </p>
 *
 * <h2>Responsibilities:</h2>
 * <ul>
 * <li>Add new borrowed money records for a user.</li>
 * <li>Update existing borrowed money details.</li>
 * <li>Delete borrowed money records by ID.</li>
 * <li>Retrieve borrowed money details by ID or by user within an optional date
 * range.</li>
 * </ul>
 *
 * <h2>Exception Handling:</h2>
 * <ul>
 * <li>{@link ResourceNotFoundException} is thrown when a user or borrowed money
 * record is not found.</li>
 * </ul>
 *
 * <h2>Usage Example:</h2>
 * 
 * <pre>
 * BorrowedMoneyRequest request = new BorrowedMoneyRequest();
 * request.setUserId(1L);
 * request.setAmount(5000.0);
 * request.setBorrowedFrom("John");
 * request.setBorrowedDate(LocalDate.now());
 * request.setDueDate(LocalDate.now().plusMonths(1));
 *
 * BorrowedMoneyResponse response = borrowedMoneyService.addBorrowedMoney(request);
 * </pre>
 *
 * @author Surya Narayanan G
 * @version 1.0
 */

@Service
public class BorrowedMoneyServiceImpl implements BorrowedMoneyService {

	@Autowired
	private BorrowedMoneyRepository borrowedMoneyRepository;

	@Autowired
	private UserRepository userRepository;

	/**
	 * Adds a new borrowed money record for a user.
	 *
	 * @param request the {@link BorrowedMoneyRequest} containing borrowed money
	 *                details
	 * @return a {@link BorrowedMoneyResponse} representing the saved record
	 * @throws ResourceNotFoundException if the user does not exist
	 */

	@Override
	public BorrowedMoneyResponse addBorrowedMoney(BorrowedMoneyRequest request) {
		User user = userRepository.findById(request.getUserId())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", request.getUserId()));

		BorrowedMoney borrowedMoney = new BorrowedMoney();
		borrowedMoney.setAmount(request.getAmount());
		borrowedMoney.setBorrowedFrom(request.getBorrowedFrom());
		borrowedMoney.setBorrowedDate(request.getBorrowedDate());
		borrowedMoney.setDueDate(request.getDueDate());
		borrowedMoney.setUser(user);

		BorrowedMoney savedBorrowedMoney = borrowedMoneyRepository.save(borrowedMoney);
		return mapToResponse(savedBorrowedMoney);
	}

	/**
	 * Updates an existing borrowed money record.
	 *
	 * @param id      the ID of the borrowed money record to update
	 * @param request the {@link BorrowedMoneyRequest} containing updated details
	 * @return a {@link BorrowedMoneyResponse} representing the updated record
	 * @throws ResourceNotFoundException if the borrowed money record does not exist
	 */

	@Override
	public BorrowedMoneyResponse updateBorrowedMoney(Long id, BorrowedMoneyRequest request) {
		BorrowedMoney borrowedMoney = borrowedMoneyRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("BorrowedMoney", "id", id));

		borrowedMoney.setAmount(request.getAmount());
		borrowedMoney.setBorrowedFrom(request.getBorrowedFrom());
		borrowedMoney.setBorrowedDate(request.getBorrowedDate());
		borrowedMoney.setDueDate(request.getDueDate());
		BorrowedMoney updatedBorrowedMoney = borrowedMoneyRepository.save(borrowedMoney);
		return mapToResponse(updatedBorrowedMoney);
	}

	/**
	 * Deletes a borrowed money record by ID.
	 *
	 * @param id the ID of the borrowed money record
	 * @throws ResourceNotFoundException if the borrowed money record does not exist
	 */

	@Override
	public void deleteBorrowedMoney(Long id) {
		if (!borrowedMoneyRepository.existsById(id)) {
			throw new ResourceNotFoundException("BorrowedMoney", "id", id);
		}
		borrowedMoneyRepository.deleteById(id);
	}

	/**
	 * Retrieves a borrowed money record by ID.
	 *
	 * @param id the ID of the borrowed money record
	 * @return a {@link BorrowedMoneyResponse} representing the record
	 * @throws ResourceNotFoundException if the borrowed money record does not exist
	 */

	@Override
	public BorrowedMoneyResponse getBorrowedMoneyById(Long id) {
		BorrowedMoney borrowedMoney = borrowedMoneyRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("BorrowedMoney", "id", id));
		return mapToResponse(borrowedMoney);
	}

	/**
	 * Retrieves all borrowed money records for a user, optionally filtered by a
	 * date range.
	 *
	 * @param userId    the ID of the user
	 * @param startDate the start date of the range (optional)
	 * @param endDate   the end date of the range (optional)
	 * @return a list of {@link BorrowedMoneyResponse} records
	 */

	@Override
	public List<BorrowedMoneyResponse> getAllBorrowedMoneyByUser(Long userId, LocalDate startDate, LocalDate endDate) {
		if (startDate != null && endDate != null) {
			return borrowedMoneyRepository.findByUserIdAndBorrowedDateBetween(userId, startDate, endDate).stream()
					.map(this::mapToResponse).collect(Collectors.toList());
		}
		return borrowedMoneyRepository.findByUserId(userId).stream().map(this::mapToResponse)
				.collect(Collectors.toList());
	}

	/**
	 * Maps a {@link BorrowedMoney} entity to a {@link BorrowedMoneyResponse}.
	 *
	 * @param borrowedMoney the entity to map
	 * @return the mapped response object
	 */

	private BorrowedMoneyResponse mapToResponse(BorrowedMoney borrowedMoney) {
		BorrowedMoneyResponse response = new BorrowedMoneyResponse();
		response.setId(borrowedMoney.getId());
		response.setAmount(borrowedMoney.getAmount());
		response.setBorrowedFrom(borrowedMoney.getBorrowedFrom());
		response.setBorrowedDate(borrowedMoney.getBorrowedDate());
		response.setDueDate(borrowedMoney.getDueDate());
		response.setUserId(borrowedMoney.getUser().getId());
		return response;
	}

}
