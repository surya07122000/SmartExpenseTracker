package com.monexel.expensetracker.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.monexel.expensetracker.entity.Expense;


/**
 * Repository interface for managing {@link Expense} entities.
 *
 * <p>This interface extends {@link JpaRepository} to provide CRUD operations
 * and custom query methods for the Expense entity in the Expense Tracker application.</p>
 *
 * <h2>Custom Query Methods:</h2>
 * <ul>
 *   <li>{@link #findByUserId(Long)} - Retrieves all expenses for a specific user.</li>
 *   <li>{@link #findByUserIdAndDateBetween(Long, LocalDate, LocalDate)} - Retrieves expenses for a user within a specified date range.</li>
 * </ul>
 *
 * <h2>Usage Example:</h2>
 * <pre>
 * List<Expense> userExpenses = expenseRepository.findByUserId(userId);
 * List<Expense> expensesInRange = expenseRepository.findByUserIdAndDateBetween(userId, startDate, endDate);
 * </pre>
 *
 * @author Surya Narayanan G
 * @version 1.0
 */

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long>{
	List<Expense> findByUserId(Long userId);
	List<Expense> findByUserIdAndDateBetween(Long userId, LocalDate startDate, LocalDate endDate);
}
