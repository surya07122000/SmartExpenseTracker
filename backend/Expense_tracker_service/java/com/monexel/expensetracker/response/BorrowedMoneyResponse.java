package com.monexel.expensetracker.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BorrowedMoneyResponse {

	private Long id;
	private BigDecimal amount;
	private String borrowedFrom;
	private LocalDate borrowedDate;
	private LocalDate dueDate;
	private Long userId;

	public BorrowedMoneyResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public BorrowedMoneyResponse(Long id, BigDecimal amount, String borrowedFrom, LocalDate borrowedDate,
			LocalDate dueDate,Long userId) {
		super();
		this.id = id;
		this.amount = amount;
		this.borrowedFrom = borrowedFrom;
		this.borrowedDate = borrowedDate;
		this.dueDate = dueDate;
		this.userId = userId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
