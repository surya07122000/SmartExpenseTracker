package com.monexel.expensetracker;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.monexel.expensetracker.entity.Category;
import com.monexel.expensetracker.entity.Expense;
import com.monexel.expensetracker.entity.Income;
import com.monexel.expensetracker.entity.User;
import com.monexel.expensetracker.exception.InsufficientFundsException;
import com.monexel.expensetracker.exception.ResourceNotFoundException;
import com.monexel.expensetracker.repository.BorrowedMoneyRepository;
import com.monexel.expensetracker.repository.CategoryRepository;
import com.monexel.expensetracker.repository.ExpenseRepository;
import com.monexel.expensetracker.repository.IncomeRepository;
import com.monexel.expensetracker.repository.UserRepository;
import com.monexel.expensetracker.request.ExpenseRequest;
import com.monexel.expensetracker.response.ExpenseResponse;
import com.monexel.expensetracker.service.ExpenseServiceImpl;


@ExtendWith(MockitoExtension.class)
public class ExpenseServiceImplTest {
	
	@Mock
	private UserRepository userRepository;
	@Mock
    private ExpenseRepository expenseRepository;
	@Mock
    private CategoryRepository categoryRepository;
    @Mock
    private IncomeRepository incomeRepository;
    @Mock
    private BorrowedMoneyRepository borrowedMoneyRepository;

    @InjectMocks
    private ExpenseServiceImpl expenseService;

    private User user;
    private Category category;
    private Expense expense;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1L);

        category = new Category();
        category.setId(1L);
        category.setName("Food");

        expense = new Expense();
        expense.setId(10L);
        expense.setTitle("Lunch");
        expense.setAmount(BigDecimal.valueOf(100));
        expense.setDate(LocalDate.now());
        expense.setUser(user);
        expense.setCategory(category);
    }

    @Test
    void testAddExpense_Success() {
        ExpenseRequest request = new ExpenseRequest();
        request.setUserId(1L);
        request.setCategoryId(1L);
        request.setTitle("Dinner");
        request.setAmount(BigDecimal.valueOf(200));
        request.setDate(LocalDate.now());

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(incomeRepository.findByUserId(1L)).thenReturn(List.of(new Income(BigDecimal.valueOf(500))));
        when(expenseRepository.findByUserId(1L)).thenReturn(List.of());
        when(borrowedMoneyRepository.findByUserId(1L)).thenReturn(List.of());
        when(expenseRepository.save(any(Expense.class))).thenReturn(expense);

        ExpenseResponse response = expenseService.addExpense(request);

        assertNotNull(response);
        assertEquals("Lunch", response.getTitle());
        verify(expenseRepository, times(1)).save(any(Expense.class));
    }

    @Test
    void testAddExpense_InsufficientFunds() {
        ExpenseRequest request = new ExpenseRequest();
        request.setUserId(1L);
        request.setCategoryId(1L);
        request.setAmount(BigDecimal.valueOf(1000));

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(incomeRepository.findByUserId(1L)).thenReturn(List.of());
        when(expenseRepository.findByUserId(1L)).thenReturn(List.of());
        when(borrowedMoneyRepository.findByUserId(1L)).thenReturn(List.of());

        assertThrows(InsufficientFundsException.class, () -> expenseService.addExpense(request));
    }

    @Test
    void testUpdateExpense_Success() {
        ExpenseRequest request = new ExpenseRequest();
        request.setTitle("Lunch");
        request.setAmount(BigDecimal.valueOf(150));
        request.setDate(LocalDate.now());
        request.setCategoryId(1L);

        when(expenseRepository.findById(10L)).thenReturn(Optional.of(expense));
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(expenseRepository.save(any(Expense.class))).thenReturn(expense);

        ExpenseResponse response = expenseService.updateExpense(10L, request);

        assertEquals("Lunch", response.getTitle()); // original title since mock returns same object
        verify(expenseRepository, times(1)).save(expense);
    }

    @Test
    void testDeleteExpense_Success() {
        when(expenseRepository.existsById(10L)).thenReturn(true);
        expenseService.deleteExpense(10L);
        verify(expenseRepository, times(1)).deleteById(10L);
    }

    @Test
    void testDeleteExpense_NotFound() {
        when(expenseRepository.existsById(10L)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> expenseService.deleteExpense(10L));
    }

    @Test
    void testGetExpenseById_Success() {
        when(expenseRepository.findById(10L)).thenReturn(Optional.of(expense));
        ExpenseResponse response = expenseService.getExpenseById(10L);
        assertEquals("Lunch", response.getTitle());
    }

    @Test
    void testGetAllExpensesByUser_WithDateRange() {
        when(expenseRepository.findByUserIdAndDateBetween(1L, LocalDate.now().minusDays(1), LocalDate.now()))
                .thenReturn(List.of(expense));

        List<ExpenseResponse> responses = expenseService.getAllExpensesByUser(1L, LocalDate.now().minusDays(1), LocalDate.now());
        assertEquals(1, responses.size());
    }

    @Test
    void testGetAllExpensesByUser_WithoutDateRange() {
        when(expenseRepository.findByUserId(1L)).thenReturn(List.of(expense));

        List<ExpenseResponse> responses = expenseService.getAllExpensesByUser(1L, null, null);
        assertEquals(1, responses.size());
    }


}
