package com.monexel.expensetracker.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


/**
 * Represents a category in the expense tracker system.
 * Categories are used to classify expenses for better organization and reporting.
 *
 * <p>Mapped to the database table <b>categories</b>.</p>
 *
 * Fields:
 * <ul>
 *   <li><b>id</b> - Unique identifier for the category.</li>
 *   <li><b>name</b> - The name of the category (must be unique, 2–30 characters).</li>
 *   <li><b>description</b> - Optional description of the category (max 100 characters).</li>
 *   <li><b>createdByUser</b> - The user who created this category (Many-to-One relationship).</li>
 * </ul>
 *
 * Validation:
 * <ul>
 *   <li>Name cannot be blank and must be between 2 and 30 characters.</li>
 *   <li>Description cannot exceed 100 characters.</li>
 * </ul>
 *
 * Relationships:
 * <ul>
 *   <li>Each category is associated with a user who created it.</li>
 * </ul>
 *
 * @author Surya Narayanan G
 * @version 1.0
 */

@Entity
@Table(name = "categories")
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Category name cannot be blank")
	@Size(min = 2, max = 30, message = "Category name must be between 2 and 30 characters")
	@Column(nullable = false, unique = true)
	private String name;

	@Size(max = 100, message = "Description cannot exceed 100 characters")
	private String description;
	

	@ManyToOne
    @JoinColumn(name = "created_by_user_id", nullable = true)
    private User createdByUser;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public User getCreatedByUser() {
		return createdByUser;
	}


	public void setCreatedByUser(User createdByUser) {
		this.createdByUser = createdByUser;
	}
	
	


}
