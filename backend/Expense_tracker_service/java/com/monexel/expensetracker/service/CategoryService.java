package com.monexel.expensetracker.service;

import java.util.List;

import com.monexel.expensetracker.request.CategoryRequest;
import com.monexel.expensetracker.response.CategoryResponse;

public interface CategoryService {

	CategoryResponse addCategory(CategoryRequest request);

	CategoryResponse updateCategory(Long id, CategoryRequest request);

	void deleteCategory(Long id);

	CategoryResponse getCategoryById(Long id);

	List<CategoryResponse> getAllCategories(); // Predefined + custom

	List<CategoryResponse> getCustomCategoriesByUser(Long userId);

}
