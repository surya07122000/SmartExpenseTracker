package com.monexel.expensetracker.service;

import java.time.LocalDate;

import com.monexel.expensetracker.response.DashboardResponse;

public interface DashboardService {
	
	DashboardResponse getDashboardSummary(Long userId, LocalDate startDate, LocalDate endDate);
}
