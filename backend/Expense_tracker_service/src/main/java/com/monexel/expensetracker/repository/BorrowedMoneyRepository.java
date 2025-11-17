package com.monexel.expensetracker.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.monexel.expensetracker.entity.BorrowedMoney;


/**
 * Repository interface for managing {@link BorrowedMoney} entities.
 *
 * <p>This interface extends {@link JpaRepository} to provide CRUD operations
 * and custom query methods for the BorrowedMoney entity in the Expense Tracker application.</p>
 *
 * <h2>Custom Query Methods:</h2>
 * <ul>
 *   <li>{@link #findByUserId(Long)} - Retrieves all borrowed money records for a specific user.</li>
 *   <li>{@link #findByUserIdAndBorrowedDateBetween(Long, LocalDate, LocalDate)} - Retrieves borrowed money records for a user within a specified date range.</li>
 * </ul>
 *
 * <h2>Usage Example:</h2>
 * <pre>
 * List<BorrowedMoney> borrowedList = borrowedMoneyRepository.findByUserId(userId);
 * List<BorrowedMoney> borrowedInRange = borrowedMoneyRepository.findByUserIdAndBorrowedDateBetween(userId, startDate, endDate);
 * </pre>
 *
 * @author Surya Narayanan G
 * @version 1.0
 */

@Repository
public interface BorrowedMoneyRepository extends JpaRepository<BorrowedMoney, Long>{
	List<BorrowedMoney> findByUserId(Long userId);
	List<BorrowedMoney> findByUserIdAndBorrowedDateBetween(Long userId, LocalDate startDate, LocalDate endDate);
}
