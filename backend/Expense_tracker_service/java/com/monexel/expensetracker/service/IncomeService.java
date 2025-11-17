package com.monexel.expensetracker.service;

import java.time.LocalDate;
import java.util.List;

import com.monexel.expensetracker.request.IncomeRequest;
import com.monexel.expensetracker.response.IncomeResponse;

public interface IncomeService {

	IncomeResponse addIncome(IncomeRequest request);
    IncomeResponse updateIncome(Long id, IncomeRequest request);
    void deleteIncome(Long id);
    IncomeResponse getIncomeById(Long id);
    List<IncomeResponse> getAllIncomesByUser(Long userId,LocalDate startDate, LocalDate endDate);

}
