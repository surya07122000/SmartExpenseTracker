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

/**
 * Service implementation for generating dashboard summaries in the Expense
 * Tracker application.
 *
 * <p>
 * This class aggregates financial data such as income, expenses, and borrowed
 * money for a given user within a specified date range. It calculates totals
 * and computes the net balance for display on the dashboard.
 * </p>
 *
 * <h2>Responsibilities:</h2>
 * <ul>
 * <li>Fetch income, expense, and borrowed money records for a user within a
 * date range.</li>
 * <li>Calculate total income, total expenses, total borrowed amount, and net
 * balance.</li>
 * <li>Return a {@link DashboardResponse} containing the aggregated data.</li>
 * </ul>
 *
 * <h2>Calculation Logic:</h2>
 * <ul>
 * <li><b>Total Income:</b> Sum of all income amounts in the date range.</li>
 * <li><b>Total Expense:</b> Sum of all expense amounts in the date range.</li>
 * <li><b>Total Borrowed:</b> Sum of all borrowed money amounts in the date
 * range.</li>
 * <li><b>Net Balance:</b> (Total Income + Total Borrowed) - Total Expense.</li>
 * </ul>
 *
 * <h2>Usage Example:</h2>
 * 
 * <pre>
 * DashboardResponse summary = dashboardService.getDashboardSummary(1L, LocalDate.now().minusMonths(1),
 * 		LocalDate.now());
 * System.out.println("Net Balance: " + summary.getNetBalance());
 * </pre>
 *
 * @author Surya Narayanan G
 * @version 1.0
 */

@Service
public class DashboardServiceImpl implements DashboardService {

	@Autowired
	private IncomeRepository incomeRepository;

	@Autowired
	private ExpenseRepository expenseRepository;

	@Autowired
	private BorrowedMoneyRepository borrowedMoneyRepository;

	/**
	 * Generates a dashboard summary for a user within a specified date range.
	 *
	 * @param userId    the ID of the user
	 * @param startDate the start date of the range (inclusive)
	 * @param endDate   the end date of the range (inclusive)
	 * @return a {@link DashboardResponse} containing total income, total expense,
	 *         total borrowed amount, and net balance
	 */

	public DashboardResponse getDashboardSummary(Long userId, LocalDate startDate, LocalDate endDate) {
		BigDecimal totalIncome = incomeRepository.findByUserIdAndDateBetween(userId, startDate, endDate).stream()
				.map(Income::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);

		BigDecimal totalExpense = expenseRepository.findByUserIdAndDateBetween(userId, startDate, endDate).stream()
				.map(Expense::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);

		BigDecimal totalBorrowed = borrowedMoneyRepository
				.findByUserIdAndBorrowedDateBetween(userId, startDate, endDate).stream().map(BorrowedMoney::getAmount)
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
