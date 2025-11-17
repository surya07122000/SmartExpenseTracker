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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.monexel.expensetracker.entity.Income;
import com.monexel.expensetracker.entity.User;
import com.monexel.expensetracker.exception.ResourceNotFoundException;
import com.monexel.expensetracker.repository.IncomeRepository;
import com.monexel.expensetracker.repository.UserRepository;
import com.monexel.expensetracker.request.IncomeRequest;
import com.monexel.expensetracker.response.IncomeResponse;
import com.monexel.expensetracker.service.IncomeServiceImpl;

public class IncomeServiceImplTest {

	@Mock
    private IncomeRepository incomeRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private IncomeServiceImpl incomeService;

    private User user;
    private Income income;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1L);

        income = new Income();
        income.setId(10L);
        income.setSource("Salary");
        income.setAmount(BigDecimal.valueOf(5000));
        income.setDate(LocalDate.now());
        income.setDescription("Monthly salary");
        income.setUser(user);
    }

    @Test
    void testAddIncome_Success() {
        IncomeRequest request = new IncomeRequest();
        request.setUserId(1L);
        request.setSource("Salary");
        request.setAmount(BigDecimal.valueOf(5000));
        request.setDate(LocalDate.now());
        request.setDescription("Monthly salary");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(incomeRepository.save(any(Income.class))).thenReturn(income);

        IncomeResponse response = incomeService.addIncome(request);

        assertNotNull(response);
        assertEquals("Salary", response.getSource());
        verify(incomeRepository, times(1)).save(any(Income.class));
    }

    @Test
    void testAddIncome_UserNotFound() {
        IncomeRequest request = new IncomeRequest();
        request.setUserId(99L);

        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> incomeService.addIncome(request));
    }

    @Test
    void testUpdateIncome_Success() {
        IncomeRequest request = new IncomeRequest();
        request.setSource("Salary");
        request.setAmount(BigDecimal.valueOf(6000));
        request.setDate(LocalDate.now());
        request.setDescription("Updated monthly salary");

        when(incomeRepository.findById(10L)).thenReturn(Optional.of(income));
        when(incomeRepository.save(any(Income.class))).thenReturn(income);

        IncomeResponse response = incomeService.updateIncome(10L, request);

        assertEquals("Salary", response.getSource()); // original mock returns same object
        verify(incomeRepository, times(1)).save(income);
    }

    @Test
    void testUpdateIncome_NotFound() {
        IncomeRequest request = new IncomeRequest();
        when(incomeRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> incomeService.updateIncome(99L, request));
    }

    @Test
    void testDeleteIncome_Success() {
        when(incomeRepository.existsById(10L)).thenReturn(true);
        incomeService.deleteIncome(10L);
        verify(incomeRepository, times(1)).deleteById(10L);
    }

    @Test
    void testDeleteIncome_NotFound() {
        when(incomeRepository.existsById(99L)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> incomeService.deleteIncome(99L));
    }

    @Test
    void testGetIncomeById_Success() {
        when(incomeRepository.findById(10L)).thenReturn(Optional.of(income));
        IncomeResponse response = incomeService.getIncomeById(10L);
        assertEquals("Salary", response.getSource());
    }

    @Test
    void testGetAllIncomesByUser_WithDateRange() {
        when(incomeRepository.findByUserIdAndDateBetween(1L, LocalDate.now().minusDays(1), LocalDate.now()))
                .thenReturn(List.of(income));

        List<IncomeResponse> responses = incomeService.getAllIncomesByUser(1L, LocalDate.now().minusDays(1), LocalDate.now());
        assertEquals(1, responses.size());
    }

    @Test
    void testGetAllIncomesByUser_WithoutDateRange() {
        when(incomeRepository.findByUserId(1L)).thenReturn(List.of(income));

        List<IncomeResponse> responses = incomeService.getAllIncomesByUser(1L, null, null);
        assertEquals(1, responses.size());
    }


}
