package com.monexel.expensetracker.response;

import java.math.BigDecimal;

public class DashboardResponse {
	private BigDecimal totalIncome;
	private BigDecimal totalExpense;
	private BigDecimal totalBorrowed;
	private BigDecimal netBalance;
	
	
	public DashboardResponse() {
		super();
	}
	public DashboardResponse(BigDecimal totalIncome, BigDecimal totalExpense, BigDecimal totalBorrowed,
			BigDecimal netBalance) {
		super();
		this.totalIncome = totalIncome;
		this.totalExpense = totalExpense;
		this.totalBorrowed = totalBorrowed;
		this.netBalance = netBalance;
	}
	public BigDecimal getTotalIncome() {
		return totalIncome;
	}
	public void setTotalIncome(BigDecimal totalIncome) {
		this.totalIncome = totalIncome;
	}
	public BigDecimal getTotalExpense() {
		return totalExpense;
	}
	public void setTotalExpense(BigDecimal totalExpense) {
		this.totalExpense = totalExpense;
	}
	public BigDecimal getTotalBorrowed() {
		return totalBorrowed;
	}
	public void setTotalBorrowed(BigDecimal totalBorrowed) {
		this.totalBorrowed = totalBorrowed;
	}
	public BigDecimal getNetBalance() {
		return netBalance;
	}
	public void setNetBalance(BigDecimal netBalance) {
		this.netBalance = netBalance;
	}
	
	

}
