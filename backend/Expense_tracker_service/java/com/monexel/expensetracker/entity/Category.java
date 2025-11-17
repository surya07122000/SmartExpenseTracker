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
