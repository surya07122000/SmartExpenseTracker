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

import com.monexel.expensetracker.entity.BorrowedMoney;
import com.monexel.expensetracker.entity.User;
import com.monexel.expensetracker.exception.ResourceNotFoundException;
import com.monexel.expensetracker.repository.BorrowedMoneyRepository;
import com.monexel.expensetracker.repository.UserRepository;
import com.monexel.expensetracker.request.BorrowedMoneyRequest;
import com.monexel.expensetracker.response.BorrowedMoneyResponse;
import com.monexel.expensetracker.service.BorrowedMoneyServiceImpl;

public class BorrowedMoneyServiceImplTest {

	@Mock
    private BorrowedMoneyRepository borrowedMoneyRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private BorrowedMoneyServiceImpl borrowedMoneyService;

    private User user;
    private BorrowedMoney borrowedMoney;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1L);

        borrowedMoney = new BorrowedMoney();
        borrowedMoney.setId(10L);
        borrowedMoney.setAmount(BigDecimal.valueOf(1000));
        borrowedMoney.setBorrowedFrom("Hari");
        borrowedMoney.setBorrowedDate(LocalDate.now());
        borrowedMoney.setDueDate(LocalDate.now().plusDays(30));
        borrowedMoney.setUser(user);
    }

    @Test
    void testAddBorrowedMoney_Success() {
        BorrowedMoneyRequest request = new BorrowedMoneyRequest();
        request.setUserId(1L);
        request.setAmount(BigDecimal.valueOf(1000));
        request.setBorrowedFrom("Hari");
        request.setBorrowedDate(LocalDate.now());
        request.setDueDate(LocalDate.now().plusDays(30));

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(borrowedMoneyRepository.save(any(BorrowedMoney.class))).thenReturn(borrowedMoney);

        BorrowedMoneyResponse response = borrowedMoneyService.addBorrowedMoney(request);

        assertNotNull(response);
        assertEquals("Hari", response.getBorrowedFrom());
        verify(borrowedMoneyRepository, times(1)).save(any(BorrowedMoney.class));
    }

    @Test
    void testAddBorrowedMoney_UserNotFound() {
        BorrowedMoneyRequest request = new BorrowedMoneyRequest();
        request.setUserId(99L);

        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> borrowedMoneyService.addBorrowedMoney(request));
    }

    @Test
    void testUpdateBorrowedMoney_Success() {
        BorrowedMoneyRequest request = new BorrowedMoneyRequest();
        request.setAmount(BigDecimal.valueOf(1500));
        request.setBorrowedFrom("Hari");
        request.setBorrowedDate(LocalDate.now());
        request.setDueDate(LocalDate.now().plusDays(40));

        when(borrowedMoneyRepository.findById(10L)).thenReturn(Optional.of(borrowedMoney));
        when(borrowedMoneyRepository.save(any(BorrowedMoney.class))).thenReturn(borrowedMoney);

        BorrowedMoneyResponse response = borrowedMoneyService.updateBorrowedMoney(10L, request);

        assertEquals("Hari", response.getBorrowedFrom()); // original mock returns same object
        verify(borrowedMoneyRepository, times(1)).save(borrowedMoney);
    }

    @Test
    void testUpdateBorrowedMoney_NotFound() {
        BorrowedMoneyRequest request = new BorrowedMoneyRequest();
        when(borrowedMoneyRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> borrowedMoneyService.updateBorrowedMoney(99L, request));
    }

    @Test
    void testDeleteBorrowedMoney_Success() {
        when(borrowedMoneyRepository.existsById(10L)).thenReturn(true);
        borrowedMoneyService.deleteBorrowedMoney(10L);
        verify(borrowedMoneyRepository, times(1)).deleteById(10L);
    }

    @Test
    void testDeleteBorrowedMoney_NotFound() {
        when(borrowedMoneyRepository.existsById(99L)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> borrowedMoneyService.deleteBorrowedMoney(99L));
    }

    @Test
    void testGetBorrowedMoneyById_Success() {
        when(borrowedMoneyRepository.findById(10L)).thenReturn(Optional.of(borrowedMoney));
        BorrowedMoneyResponse response = borrowedMoneyService.getBorrowedMoneyById(10L);
        assertEquals("Hari", response.getBorrowedFrom());
    }

    @Test
    void testGetAllBorrowedMoneyByUser_WithDateRange() {
        when(borrowedMoneyRepository.findByUserIdAndBorrowedDateBetween(1L, LocalDate.now().minusDays(1), LocalDate.now()))
                .thenReturn(List.of(borrowedMoney));

        List<BorrowedMoneyResponse> responses = borrowedMoneyService.getAllBorrowedMoneyByUser(1L, LocalDate.now().minusDays(1), LocalDate.now());
        assertEquals(1, responses.size());
    }

    @Test
    void testGetAllBorrowedMoneyByUser_WithoutDateRange() {
        when(borrowedMoneyRepository.findByUserId(1L)).thenReturn(List.of(borrowedMoney));

        List<BorrowedMoneyResponse> responses = borrowedMoneyService.getAllBorrowedMoneyByUser(1L, null, null);
        assertEquals(1, responses.size());
    }


}
