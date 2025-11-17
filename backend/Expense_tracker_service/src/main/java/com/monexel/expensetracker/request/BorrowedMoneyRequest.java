package com.monexel.expensetracker.request;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class BorrowedMoneyRequest {

	@NotNull(message = "Amount is required")
	@Positive(message = "Amount must be greater than zero")
	private BigDecimal amount;
	@NotBlank(message = "Lender name is required")
	private String borrowedFrom; // Friend, Bank, Finance Company
	@NotNull(message = "Borrowed date is required")
	private LocalDate borrowedDate;
	private LocalDate dueDate;
	@NotNull(message = "Borrowed date is required")
	private Long userId;

	public BorrowedMoneyRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public BorrowedMoneyRequest(BigDecimal amount, String borrowedFrom, LocalDate borrowedDate, LocalDate dueDate,
			 Long userId) {
		super();
		this.amount = amount;
		this.borrowedFrom = borrowedFrom;
		this.borrowedDate = borrowedDate;
		this.dueDate = dueDate;
		this.userId = userId;
	}



	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getBorrowedFrom() {
		return borrowedFrom;
	}

	public void setBorrowedFrom(String borrowedFrom) {
		this.borrowedFrom = borrowedFrom;
	}

	public LocalDate getBorrowedDate() {
		return borrowedDate;
	}

	public void setBorrowedDate(LocalDate borrowedDate) {
		this.borrowedDate = borrowedDate;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
