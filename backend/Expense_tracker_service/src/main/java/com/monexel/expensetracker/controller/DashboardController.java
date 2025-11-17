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

/**
 * REST controller for providing dashboard summary data. This controller exposes
 * endpoints to retrieve expense dashboard summaries for a user, optionally
 * filtered by a date range.
 * @author Surya Narayanan G
 * @version 1.0
 */

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

	@Autowired
	private DashboardService dashboardService;

	/**
	 * Retrieves the dashboard summary for a specific user. If no start and end
	 * dates are provided, defaults to the current month.
	 *
	 * @param userId    the ID of the user whose dashboard summary is requested
	 * @param startDate optional start date for filtering (ISO format: yyyy-MM-dd)
	 * @param endDate   optional end date for filtering (ISO format: yyyy-MM-dd)
	 * @return ResponseEntity containing the {@link DashboardResponse} with summary
	 *         details
	 *
	 *         Example:
	 * 
	 *         <pre>
	 * GET /api/dashboard/getDashboardSummary/1?startDate=2025-11-01&endDate=2025-11-30
	 *         </pre>
	 */

	@GetMapping("/getDashboardSummary/{userId}")
	public ResponseEntity<DashboardResponse> getDashboardSummary(@PathVariable Long userId,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

		if (startDate == null || endDate == null) {
			LocalDate now = LocalDate.now();
			startDate = now.withDayOfMonth(1);
			endDate = now.withDayOfMonth(now.lengthOfMonth());
		}

		return ResponseEntity.ok(dashboardService.getDashboardSummary(userId, startDate, endDate));
	}

}
