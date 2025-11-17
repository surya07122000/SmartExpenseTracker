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

@Service
public class IncomeServiceImpl implements IncomeService{
	

	@Autowired
    private IncomeRepository incomeRepository;

    @Autowired
    private UserRepository userRepository;

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

    @Override
    public void deleteIncome(Long id) {
        if (!incomeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Income", "id", id);
        }
        incomeRepository.deleteById(id);
    }

    @Override
    public IncomeResponse getIncomeById(Long id) {
        Income income = incomeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Income", "id", id));
        return mapToResponse(income);
    }

    @Override
    public List<IncomeResponse> getAllIncomesByUser(Long userId,LocalDate startDate, LocalDate endDate) {

    	if (startDate != null && endDate != null) {
			return incomeRepository.findByUserIdAndDateBetween(userId, startDate, endDate).stream()
					.map(this::mapToResponse).collect(Collectors.toList());
    }

        return incomeRepository.findByUserId(userId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

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
