package com.monexel.expensetracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.monexel.expensetracker.request.CategoryRequest;
import com.monexel.expensetracker.response.CategoryResponse;
import com.monexel.expensetracker.service.CategoryService;

import jakarta.validation.Valid;

/**
 * REST controller for managing expense categories. Provides endpoints to
 * create, update, delete, and retrieve categories.
 * @author Surya Narayanan G
 * @version 1.0
 */

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	/**
	 * Adds a new category.
	 *
	 * @param request the {@link CategoryRequest} containing category details
	 * @return ResponseEntity containing the created {@link CategoryResponse} and
	 *         HTTP status 201 (Created)
	 */

	@PostMapping("/addCategory")
	public ResponseEntity<CategoryResponse> addCategory(@RequestBody @Valid CategoryRequest request) {
		return new ResponseEntity<>(categoryService.addCategory(request), HttpStatus.CREATED);
	}

	/**
	 * Updates an existing category by ID.
	 *
	 * @param id      the ID of the category to update
	 * @param request the {@link CategoryRequest} with updated details
	 * @return ResponseEntity containing the updated {@link CategoryResponse}
	 * @throws ResourceNotFoundException if the category with given ID does not
	 *                                   exist
	 */

	@PutMapping("/updateCategory/{id}")
	public ResponseEntity<CategoryResponse> updateCategory(@PathVariable Long id,
			@RequestBody @Valid CategoryRequest request) {
		return ResponseEntity.ok(categoryService.updateCategory(id, request));
	}

	/**
	 * Deletes a category by ID.
	 *
	 * @param id the ID of the category to delete
	 * @return ResponseEntity with HTTP status 204 (No Content)
	 * @throws ResourceNotFoundException if the category with given ID does not
	 *                                   exist
	 */

	@DeleteMapping("/deleteCategory/{id}")
	public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
		categoryService.deleteCategory(id);
		return ResponseEntity.noContent().build();
	}

	/**
	 * Retrieves a category by ID.
	 *
	 * @param id the ID of the category
	 * @return ResponseEntity containing the {@link CategoryResponse}
	 * @throws ResourceNotFoundException if the category with given ID does not
	 *                                   exist
	 */

	@GetMapping("/getCategoryById/{id}")
	public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Long id) {
		return ResponseEntity.ok(categoryService.getCategoryById(id));
	}

	/**
	 * Retrieves all categories.
	 *
	 * @return ResponseEntity containing a list of {@link CategoryResponse}
	 */

	@GetMapping("/getAllCategories")
	public ResponseEntity<List<CategoryResponse>> getAllCategories() {
		return ResponseEntity.ok(categoryService.getAllCategories());
	}

	/**
	 * Retrieves custom categories created by a specific user.
	 *
	 * @param userId the ID of the user
	 * @return ResponseEntity containing a list of {@link CategoryResponse}
	 */

	@GetMapping("/getCustomCategoryByUser/{userId}")
	public ResponseEntity<List<CategoryResponse>> getCustomCategoriesByUser(@PathVariable Long userId) {
		return ResponseEntity.ok(categoryService.getCustomCategoriesByUser(userId));
	}

}
