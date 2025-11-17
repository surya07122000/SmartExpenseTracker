package com.monexel.expensetracker.service;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monexel.expensetracker.entity.BorrowedMoney;
import com.monexel.expensetracker.entity.Expense;
import com.monexel.expensetracker.entity.Income;
import com.monexel.expensetracker.repository.BorrowedMoneyRepository;
import com.monexel.expensetracker.repository.ExpenseRepository;
import com.monexel.expensetracker.repository.IncomeRepository;
import com.monexel.expensetracker.response.DashboardResponse;


@Service
public class DashboardServiceImpl implements DashboardService {

	@Autowired
    private IncomeRepository incomeRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private BorrowedMoneyRepository borrowedMoneyRepository;

    

public DashboardResponse getDashboardSummary(Long userId, LocalDate startDate, LocalDate endDate) {
        BigDecimal totalIncome = incomeRepository.findByUserIdAndDateBetween(userId, startDate, endDate)
                .stream()
                .map(Income::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalExpense = expenseRepository.findByUserIdAndDateBetween(userId, startDate, endDate)
                .stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalBorrowed = borrowedMoneyRepository.findByUserIdAndBorrowedDateBetween(userId, startDate, endDate)
                .stream()
                .map(BorrowedMoney::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal netBalance = (totalIncome.add(totalBorrowed)).subtract(totalExpense);

        DashboardResponse response = new DashboardResponse();
        response.setTotalIncome(totalIncome);
        response.setTotalExpense(totalExpense);
        response.setTotalBorrowed(totalBorrowed);
        response.setNetBalance(netBalance);

        return response;
    }



}
