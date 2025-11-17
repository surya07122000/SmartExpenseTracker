package com.monexel.expensetracker.request;

import jakarta.validation.constraints.NotBlank;

public class CategoryRequest {

	@NotBlank(message = "Category name is required")
	private String name;
    private String description;
    private Long userId;
    
	public CategoryRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CategoryRequest(String name, String description, Long userId) {
		super();
		this.name = name;
		this.description = description;
		this.userId = userId;
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
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
    
    
}
