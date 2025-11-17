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

@Service
public class BorrowedMoneyServiceImpl implements BorrowedMoneyService {

	@Autowired
	private BorrowedMoneyRepository borrowedMoneyRepository;

	@Autowired
	private UserRepository userRepository;

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

	@Override
	public void deleteBorrowedMoney(Long id) {
		if (!borrowedMoneyRepository.existsById(id)) {
			throw new ResourceNotFoundException("BorrowedMoney", "id", id);
		}
		borrowedMoneyRepository.deleteById(id);
	}

	@Override
	public BorrowedMoneyResponse getBorrowedMoneyById(Long id) {
		BorrowedMoney borrowedMoney = borrowedMoneyRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("BorrowedMoney", "id", id));
		return mapToResponse(borrowedMoney);
	}

	@Override
	public List<BorrowedMoneyResponse> getAllBorrowedMoneyByUser(Long userId,LocalDate startDate, LocalDate endDate) {
		if (startDate != null && endDate != null) {
			return borrowedMoneyRepository.findByUserIdAndBorrowedDateBetween(userId, startDate, endDate).stream()
					.map(this::mapToResponse).collect(Collectors.toList());
    }
		return borrowedMoneyRepository.findByUserId(userId).stream().map(this::mapToResponse)
				.collect(Collectors.toList());
	}

	

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
