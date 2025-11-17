package com.monexel.expensetracker.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


/**
 * Represents an income record in the expense tracker system.
 * This entity stores details about income transactions, including source,
 * amount, date, description, and the associated user.
 *
 * <p>Mapped to the database table <b>income</b>.</p>
 *
 * Fields:
 * <ul>
 *   <li><b>id</b> - Unique identifier for the income record.</li>
 *   <li><b>source</b> - The source of income (e.g., Salary, Business) (cannot be blank).</li>
 *   <li><b>amount</b> - The monetary value of the income (cannot be null).</li>
 *   <li><b>date</b> - The date when the income was received (cannot be null).</li>
 *   <li><b>description</b> - Additional details about the income (cannot be blank).</li>
 *   <li><b>user</b> - The user associated with this income record (Many-to-One relationship).</li>
 * </ul>
 *
 * Validation:
 * <ul>
 *   <li>Source and description must not be blank.</li>
 *   <li>Amount and date must not be null.</li>
 * </ul>
 *
 * Relationships:
 * <ul>
 *   <li>Each income record is linked to one user.</li>
 * </ul>
 *
 * @author Surya Narayanan G
 * @version 1.0
 */

@Entity
@Table(name = "income")
public class Income {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Source cannot be blank")
	@Column(nullable = false)
	private String source; 

	@NotNull(message = "Amount cannot be null")
	@Column(nullable = false)
	private BigDecimal amount;

	@NotNull(message = "Date cannot be null")
	@Column(nullable = false)
	private LocalDate date;
	
	@NotBlank(message = "Description cannot be blank")
	@Column(nullable = false)
	private String description;
	
	

	public Income() {
		super();
		
	}

	public Income(BigDecimal valueOf) {
	    this.amount = valueOf;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	

}
