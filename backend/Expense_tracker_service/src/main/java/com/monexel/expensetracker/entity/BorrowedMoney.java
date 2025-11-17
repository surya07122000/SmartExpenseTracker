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
 * Represents a borrowed money transaction in the expense tracker system.
 * This entity stores details about the borrowed amount, lender information,
 * borrowed date, due date, and the associated user.
 *
 * <p>Mapped to the database table <b>borrowed_money</b>.</p>
 *
 * Fields:
 * <ul>
 *   <li><b>id</b> - Unique identifier for the borrowed money record.</li>
 *   <li><b>amount</b> - The amount borrowed (cannot be null).</li>
 *   <li><b>borrowedFrom</b> - The source from which money was borrowed (e.g., Friend, Bank).</li>
 *   <li><b>borrowedDate</b> - The date when the money was borrowed.</li>
 *   <li><b>dueDate</b> - The date by which the borrowed amount should be repaid.</li>
 *   <li><b>user</b> - The user who borrowed the money (Many-to-One relationship).</li>
 * </ul>
 *
 * Validation:
 * <ul>
 *   <li>Amount must not be null.</li>
 *   <li>BorrowedFrom must not be blank.</li>
 *   <li>BorrowedDate and DueDate must not be null.</li>
 * </ul>
 *
 * @author Surya Narayanan G
 * @version 1.0
 */

@Entity
@Table(name = "borrowed_money")
public class BorrowedMoney {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "Amount cannot be null")
	@Column(nullable = false)
	private BigDecimal amount;

	@NotBlank(message = "Borrowed from cannot be blank")
	@Column(nullable = false)
	private String borrowedFrom; // Friend or Bank

	@NotNull(message = "Borrowed date cannot be null")
	@Column(nullable = false)
	private LocalDate borrowedDate;

	@NotNull(message = "Due date cannot be null")
	@Column(nullable = false)
	private LocalDate dueDate;



	// Relationship with User
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	

}
