package com.monexel.expensetracker.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.monexel.expensetracker.entity.Income;


/**
 * Repository interface for managing {@link Income} entities.
 *
 * <p>This interface extends {@link JpaRepository} to provide CRUD operations
 * and custom query methods for the Income entity in the Expense Tracker application.</p>
 *
 * <h2>Custom Query Methods:</h2>
 * <ul>
 *   <li>{@link #findByUserId(Long)} - Retrieves all income records for a specific user.</li>
 *   <li>{@link #findByUserIdAndDateBetween(Long, LocalDate, LocalDate)} - Retrieves income records for a user within a specified date range.</li>
 * </ul>
 *
 * <h2>Usage Example:</h2>
 * <pre>
 * List<Income> userIncome = incomeRepository.findByUserId(userId);
 * List<Income> incomeInRange = incomeRepository.findByUserIdAndDateBetween(userId, startDate, endDate);
 * </pre>
 *
 * @author Surya Narayanan G
 * @version 1.0
 */

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {
    List<Income> findByUserId(Long userId);
    List<Income> findByUserIdAndDateBetween(Long userId, LocalDate startDate, LocalDate endDate);

}
