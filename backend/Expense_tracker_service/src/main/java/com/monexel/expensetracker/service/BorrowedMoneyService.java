package com.monexel.expensetracker.service;


import java.time.LocalDate;
import java.util.List;

import com.monexel.expensetracker.request.BorrowedMoneyRequest;
import com.monexel.expensetracker.response.BorrowedMoneyResponse;

public interface BorrowedMoneyService {

	BorrowedMoneyResponse addBorrowedMoney(BorrowedMoneyRequest request);
    BorrowedMoneyResponse updateBorrowedMoney(Long id, BorrowedMoneyRequest request);
    void deleteBorrowedMoney(Long id);
    BorrowedMoneyResponse getBorrowedMoneyById(Long id);
    List<BorrowedMoneyResponse> getAllBorrowedMoneyByUser(Long userId,LocalDate startDate, LocalDate endDate);
   

}
