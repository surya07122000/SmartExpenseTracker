package com.monexel.expensetracker.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public class IncomeResponse {

	private Long id;
    private String source;
    private BigDecimal amount;
    private LocalDate date;
    private String description;
    private Long userId;
	public IncomeResponse(Long id, String source, BigDecimal amount, LocalDate date, String description, Long userId) {
		super();
		this.id = id;
		this.source = source;
		this.amount = amount;
		this.date = date;
		this.description = description;
		this.userId = userId;
	}
	public IncomeResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
    
    


}
