package com.monexel.expensetracker.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monexel.expensetracker.entity.Category;
import com.monexel.expensetracker.entity.User;
import com.monexel.expensetracker.exception.ResourceNotFoundException;
import com.monexel.expensetracker.repository.CategoryRepository;
import com.monexel.expensetracker.repository.UserRepository;
import com.monexel.expensetracker.request.CategoryRequest;
import com.monexel.expensetracker.response.CategoryResponse;

/**
 * Service implementation for managing categories in the Expense Tracker
 * application.
 *
 * <p>
 * This class provides business logic for creating, updating, deleting, and
 * retrieving category details. It interacts with {@link CategoryRepository} for
 * persistence and {@link UserRepository} for associating categories with users.
 * </p>
 *
 * <h2>Responsibilities:</h2>
 * <ul>
 * <li>Add new categories (optionally linked to a user).</li>
 * <li>Update existing category details.</li>
 * <li>Delete categories by ID.</li>
 * <li>Retrieve category details by ID or fetch all categories.</li>
 * <li>Retrieve custom categories created by a specific user.</li>
 * </ul>
 *
 * <h2>Exception Handling:</h2>
 * <ul>
 * <li>{@link ResourceNotFoundException} is thrown when a user or category is
 * not found.</li>
 * </ul>
 *
 * <h2>Usage Example:</h2>
 * 
 * <pre>
 * CategoryRequest request = new CategoryRequest();
 * request.setName("Food");
 * request.setDescription("Expenses related to food");
 * request.setUserId(1L);
 *
 * CategoryResponse response = categoryService.addCategory(request);
 * </pre>
 *
 * @author Surya Narayanan G
 * @version 1.0
 */

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private UserRepository userRepository;

	/**
	 * Adds a new category. Optionally associates it with a user.
	 *
	 * @param request the {@link CategoryRequest} containing category details
	 * @return a {@link CategoryResponse} representing the saved category
	 * @throws ResourceNotFoundException if the specified user does not exist
	 */

	@Override
	public CategoryResponse addCategory(CategoryRequest request) {
		Category category = new Category();
		category.setName(request.getName());
		category.setDescription(request.getDescription());

		if (request.getUserId() != null) {
			User user = userRepository.findById(request.getUserId())
					.orElseThrow(() -> new ResourceNotFoundException("User", "id", request.getUserId()));
			category.setCreatedByUser(user);
		}

		Category savedCategory = categoryRepository.save(category);
		return mapToResponse(savedCategory);
	}

	/**
	 * Updates an existing category.
	 *
	 * @param id      the ID of the category to update
	 * @param request the {@link CategoryRequest} containing updated details
	 * @return a {@link CategoryResponse} representing the updated category
	 * @throws ResourceNotFoundException if the category does not exist
	 */

	@Override
	public CategoryResponse updateCategory(Long id, CategoryRequest request) {
		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));

		category.setName(request.getName());
		category.setDescription(request.getDescription());

		Category updatedCategory = categoryRepository.save(category);
		return mapToResponse(updatedCategory);
	}

	/**
	 * Deletes a category by ID.
	 *
	 * @param id the ID of the category
	 * @throws ResourceNotFoundException if the category does not exist
	 */

	@Override
	public void deleteCategory(Long id) {
		if (!categoryRepository.existsById(id)) {
			throw new ResourceNotFoundException("Category", "id", id);
		}
		categoryRepository.deleteById(id);
	}

	/**
	 * Retrieves a category by ID.
	 *
	 * @param id the ID of the category
	 * @return a {@link CategoryResponse} representing the category
	 * @throws ResourceNotFoundException if the category does not exist
	 */

	@Override
	public CategoryResponse getCategoryById(Long id) {
		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
		return mapToResponse(category);
	}

	/**
	 * Retrieves all categories.
	 *
	 * @return a list of {@link CategoryResponse} representing all categories
	 */

	@Override
	public List<CategoryResponse> getAllCategories() {
		return categoryRepository.findAll().stream().map(this::mapToResponse).collect(Collectors.toList());
	}

	/**
	 * Retrieves all custom categories created by a specific user.
	 *
	 * @param userId the ID of the user
	 * @return a list of {@link CategoryResponse} representing the user's categories
	 */

	@Override
	public List<CategoryResponse> getCustomCategoriesByUser(Long userId) {
		return categoryRepository.findByCreatedByUserId(userId).stream().map(this::mapToResponse)
				.collect(Collectors.toList());
	}

	/**
	 * Maps a {@link Category} entity to a {@link CategoryResponse}.
	 *
	 * @param category the entity to map
	 * @return the mapped response object
	 */

	private CategoryResponse mapToResponse(Category category) {
		CategoryResponse response = new CategoryResponse();
		response.setId(category.getId());
		response.setName(category.getName());
		response.setDescription(category.getDescription());
		response.setCreatedByUserId(category.getCreatedByUser() != null ? category.getCreatedByUser().getId() : null);
		return response;
	}

}
