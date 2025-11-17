package com.monexel.expensetracker;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.monexel.expensetracker.entity.BorrowedMoney;
import com.monexel.expensetracker.entity.Expense;
import com.monexel.expensetracker.entity.Income;
import com.monexel.expensetracker.repository.BorrowedMoneyRepository;
import com.monexel.expensetracker.repository.ExpenseRepository;
import com.monexel.expensetracker.repository.IncomeRepository;
import com.monexel.expensetracker.response.DashboardResponse;
import com.monexel.expensetracker.service.DashboardServiceImpl;

@ExtendWith(MockitoExtension.class)
public class DashboardServiceImplTest {
	

	@Mock
    private IncomeRepository incomeRepository;

    @Mock
    private ExpenseRepository expenseRepository;

    @Mock
    private BorrowedMoneyRepository borrowedMoneyRepository;

    @InjectMocks
    private DashboardServiceImpl dashboardService;

    private Income income;
    private Expense expense;
    private BorrowedMoney borrowedMoney;

    @BeforeEach
    void setUp() {
        income = new Income();
        income.setAmount(BigDecimal.valueOf(5000));

        expense = new Expense();
        expense.setAmount(BigDecimal.valueOf(2000));

        borrowedMoney = new BorrowedMoney();
        borrowedMoney.setAmount(BigDecimal.valueOf(1000));
    }


@Test
    void testGetDashboardSummary_Success() {
        Long userId = 1L;
        LocalDate startDate = LocalDate.now().minusDays(30);
        LocalDate endDate = LocalDate.now();

        when(incomeRepository.findByUserIdAndDateBetween(userId, startDate, endDate))
                .thenReturn(List.of(income));
        when(expenseRepository.findByUserIdAndDateBetween(userId, startDate, endDate))
                .thenReturn(List.of(expense));
        when(borrowedMoneyRepository.findByUserIdAndBorrowedDateBetween(userId, startDate, endDate))
                .thenReturn(List.of(borrowedMoney));

        DashboardResponse response = dashboardService.getDashboardSummary(userId, startDate, endDate);

        assertNotNull(response);
        assertEquals(BigDecimal.valueOf(5000), response.getTotalIncome());
        assertEquals(BigDecimal.valueOf(2000), response.getTotalExpense());
        assertEquals(BigDecimal.valueOf(1000), response.getTotalBorrowed());
        assertEquals(BigDecimal.valueOf(4000), response.getNetBalance()); // 5000 + 1000 - 2000
    }

    @Test
    void testGetDashboardSummary_EmptyLists() {
        Long userId = 1L;
        LocalDate startDate = LocalDate.now().minusDays(30);
        LocalDate endDate = LocalDate.now();

        when(incomeRepository.findByUserIdAndDateBetween(userId, startDate, endDate))
                .thenReturn(List.of());
        when(expenseRepository.findByUserIdAndDateBetween(userId, startDate, endDate))
                .thenReturn(List.of());
        when(borrowedMoneyRepository.findByUserIdAndBorrowedDateBetween(userId, startDate, endDate))
                .thenReturn(List.of());

        DashboardResponse response = dashboardService.getDashboardSummary(userId, startDate, endDate);

        assertEquals(BigDecimal.ZERO, response.getTotalIncome());
        assertEquals(BigDecimal.ZERO, response.getTotalExpense());
        assertEquals(BigDecimal.ZERO, response.getTotalBorrowed());
        assertEquals(BigDecimal.ZERO, response.getNetBalance());
    }



}
