package com.monexel.expensetracker.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.monexel.expensetracker.entity.BorrowedMoney;

@Repository
public interface BorrowedMoneyRepository extends JpaRepository<BorrowedMoney, Long>{
	List<BorrowedMoney> findByUserId(Long userId);
	List<BorrowedMoney> findByUserIdAndBorrowedDateBetween(Long userId, LocalDate startDate, LocalDate endDate);
}
