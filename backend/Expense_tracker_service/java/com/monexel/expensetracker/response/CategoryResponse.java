package com.monexel.expensetracker.response;

public class CategoryResponse {

private Long id;
    private String name;
    private String description;
    private Long createdByUserId;
    
    
	public CategoryResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CategoryResponse(Long id, String name, String description, Long createdByUserId) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.createdByUserId = createdByUserId;
	}
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
	public Long getCreatedByUserId() {
		return createdByUserId;
	}
	public void setCreatedByUserId(Long createdByUserId) {
		this.createdByUserId = createdByUserId;
	} 


}
