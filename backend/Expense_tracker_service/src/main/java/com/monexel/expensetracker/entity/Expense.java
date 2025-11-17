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
 * Represents an expense record in the expense tracker system.
 * This entity stores details about an expense, including title, amount,
 * date, and its associations with a category and a user.
 *
 * <p>Mapped to the database table <b>expenses</b>.</p>
 *
 * Fields:
 * <ul>
 *   <li><b>id</b> - Unique identifier for the expense.</li>
 *   <li><b>title</b> - Title or description of the expense (cannot be blank).</li>
 *   <li><b>amount</b> - Monetary value of the expense (cannot be null).</li>
 *   <li><b>date</b> - Date when the expense occurred (cannot be null).</li>
 *   <li><b>category</b> - Associated category for the expense (Many-to-One relationship).</li>
 *   <li><b>user</b> - The user who recorded the expense (Many-to-One relationship).</li>
 * </ul>
 *
 * Validation:
 * <ul>
 *   <li>Title must not be blank.</li>
 *   <li>Amount and Date must not be null.</li>
 * </ul>
 *
 * Relationships:
 * <ul>
 *   <li>Each expense belongs to one category.</li>
 *   <li>Each expense is linked to one user.</li>
 * </ul>
 *
 * @author Surya Narayanan G
 * @version 1.0
 */

@Entity
@Table(name = "expenses")
public class Expense {
	

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title cannot be blank")
    @Column(nullable = false)
    private String title;

    @NotNull(message = "Amount cannot be null")
    @Column(nullable = false)
    private BigDecimal amount;

    @NotNull(message = "Date cannot be null")
    @Column(nullable = false)
    private LocalDate date;

    // Relationship with Category
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
    


}
