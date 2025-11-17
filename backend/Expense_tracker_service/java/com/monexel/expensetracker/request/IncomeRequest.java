package com.monexel.expensetracker.request;

import java.math.BigDecimal;
import java.time.LocalDate;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class IncomeRequest {


	@NotBlank(message = "Source cannot be blank")
	private String source; 

	@NotNull(message = "Amount cannot be null")
	@Positive(message = "Amount must be greater than zero")
	private BigDecimal amount;

	@NotNull(message = "Date cannot be null")
	private LocalDate date;
	
	@NotBlank(message = "Description cannot be blank")
	private String description;
	@NotNull(message = "User ID is required")
	private Long userId;
	
    
	
	public IncomeRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	public IncomeRequest(String source, BigDecimal amount, LocalDate date, String description, Long userId) {
		super();
		this.source = source;
		this.amount = amount;
		this.date = date;
		this.description = description;
		this.userId = userId;
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
