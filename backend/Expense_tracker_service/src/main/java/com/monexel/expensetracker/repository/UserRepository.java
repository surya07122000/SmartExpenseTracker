package com.monexel.expensetracker.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.monexel.expensetracker.entity.User;

/**
 * Repository interface for managing {@link User} entities.
 *
 * <p>This interface extends {@link JpaRepository} to provide CRUD operations
 * and custom query methods for the User entity in the Expense Tracker application.</p>
 *
 * <h2>Custom Query Methods:</h2>
 * <ul>
 *   <li>{@link #findByEmail(String)} - Retrieves a user by email.</li>
 *   <li>{@link #existsByEmail(String)} - Checks if a user exists by email.</li>
 *   <li>{@link #existsByPhoneNumber(String)} - Checks if a user exists by phone number.</li>
 *   <li>{@link #findByName(String)} - Retrieves a user by name (wrapped in {@link Optional}).</li>
 *   <li>{@link #existsByName(String)} - Checks if a user exists by name.</li>
 *   <li>{@link #findUsersByEmail(String)} - Retrieves user details by email (custom implementation may be required).</li>
 * </ul>
 *
 * <h2>Usage Example:</h2>
 * <pre>
 * User user = userRepository.findByEmail("test@example.com");
 * boolean exists = userRepository.existsByEmail("test@example.com");
 * Optional<User> userOpt = userRepository.findByName("John");
 * </pre>
 *
 * @author Surya Narayanan G
 * @version 1.0
 */


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
	Optional<User> findByName(String userName);
	boolean existsByName(String userName);
	Object findUsersByEmail(String username);

	


}
