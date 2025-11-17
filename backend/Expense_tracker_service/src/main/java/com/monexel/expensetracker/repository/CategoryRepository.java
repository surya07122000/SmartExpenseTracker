package com.monexel.expensetracker.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.monexel.expensetracker.entity.Category;


/**
 * Repository interface for managing {@link Category} entities.
 *
 * <p>This interface extends {@link JpaRepository} to provide CRUD operations
 * and custom query methods for the Category entity in the Expense Tracker application.</p>
 *
 * <h2>Custom Query Methods:</h2>
 * <ul>
 *   <li>{@link #existsByName(String)} - Checks if a category with the given name already exists.</li>
 *   <li>{@link #findByCreatedByUserId(Long)} - Retrieves all categories created by a specific user.</li>
 * </ul>
 *
 * <h2>Usage Example:</h2>
 * <pre>
 * boolean exists = categoryRepository.existsByName("Food");
 * List<Category> userCategories = categoryRepository.findByCreatedByUserId(userId);
 * </pre>
 *
 * @author Surya Narayanan G
 * @version 1.0
 */

@Repository
public interface CategoryRepository  extends JpaRepository<Category, Long>{

	boolean existsByName(String name);
	List<Category> findByCreatedByUserId(Long userId);
}
