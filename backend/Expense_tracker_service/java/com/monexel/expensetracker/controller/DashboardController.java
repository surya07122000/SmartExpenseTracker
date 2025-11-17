package com.monexel.expensetracker.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.monexel.expensetracker.response.DashboardResponse;
import com.monexel.expensetracker.service.DashboardService;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
	

	@Autowired
    private DashboardService dashboardService;


    @GetMapping("/getDashboardSummary/{userId}")
    public ResponseEntity<DashboardResponse> getDashboardSummary(
            @PathVariable Long userId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        // Default to current month if no dates provided
        if (startDate == null || endDate == null) {
            LocalDate now = LocalDate.now();
            startDate = now.withDayOfMonth(1);
            endDate = now.withDayOfMonth(now.lengthOfMonth());
        }

        return ResponseEntity.ok(dashboardService.getDashboardSummary(userId, startDate, endDate));
    }



}
