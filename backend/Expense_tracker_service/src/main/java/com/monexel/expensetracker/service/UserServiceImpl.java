package com.monexel.expensetracker.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.monexel.expensetracker.entity.User;
import com.monexel.expensetracker.exception.ResourceNotFoundException;
import com.monexel.expensetracker.repository.UserRepository;
import com.monexel.expensetracker.request.UserRequest;
import com.monexel.expensetracker.response.UserResponse;

/**
 * Service implementation for managing user accounts in the Expense Tracker
 * application.
 *
 * <p>
 * This class provides business logic for creating, updating, deleting, and
 * retrieving user details. It also handles password encryption and password
 * update functionality.
 * </p>
 *
 * <h2>Responsibilities:</h2>
 * <ul>
 * <li>Create new users with encrypted passwords.</li>
 * <li>Retrieve user details by ID or email.</li>
 * <li>Update user details and passwords.</li>
 * <li>Delete users by ID.</li>
 * </ul>
 *
 * <h2>Security:</h2>
 * <ul>
 * <li>Passwords are encrypted using {@link PasswordEncoder} before saving to
 * the database.</li>
 * </ul>
 *
 * <h2>Exception Handling:</h2>
 * <ul>
 * <li>{@link ResourceNotFoundException} is thrown when a user is not found by
 * ID or email.</li>
 * </ul>
 *
 * <h2>Usage Example:</h2>
 * 
 * <pre>
 * UserRequest request = new UserRequest();
 * request.setName("John Doe");
 * request.setEmail("john@example.com");
 * request.setPassword("securePassword");
 * request.setPhoneNumber("9876543210");
 * request.setRole("USER");
 *
 * UserResponse response = userService.createUser(request);
 * </pre>
 *
 * @author Surya Narayanan G
 * @version 1.0
 */

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * Creates a new user with encrypted password.
	 *
	 * @param request the {@link UserRequest} containing user details
	 * @return a {@link UserResponse} representing the saved user
	 */

	@Override
	public UserResponse createUser(UserRequest request) {
		User user = new User();
		user.setName(request.getName());
		user.setEmail(request.getEmail());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setPhoneNumber(request.getPhoneNumber());
		user.setRole(request.getRole());

		User savedUser = userRepository.save(user);
		return mapToResponse(savedUser);
	}

	/**
	 * Retrieves a user by ID.
	 *
	 * @param id the ID of the user
	 * @return a {@link UserResponse} representing the user
	 * @throws ResourceNotFoundException if the user does not exist
	 */

	@Override
	public UserResponse getUserById(Long id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
		return mapToResponse(user);
	}

	/**
	 * Retrieves all users.
	 *
	 * @return a list of {@link UserResponse} representing all users
	 */

	@Override
	public List<UserResponse> getAllUsers() {
		return userRepository.findAll().stream().map(this::mapToResponse).collect(Collectors.toList());
	}

	/**
	 * Updates an existing user.
	 *
	 * @param id      the ID of the user to update
	 * @param request the {@link UserRequest} containing updated details
	 * @return a {@link UserResponse} representing the updated user
	 * @throws ResourceNotFoundException if the user does not exist
	 */

	@Override
	public UserResponse updateUser(Long id, UserRequest request) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

		user.setName(request.getName());
		user.setEmail(request.getEmail());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setPhoneNumber(request.getPhoneNumber());
		user.setRole(request.getRole());

		User updatedUser = userRepository.save(user);
		return mapToResponse(updatedUser);
	}

	/**
	 * Deletes a user by ID.
	 *
	 * @param id the ID of the user
	 * @throws ResourceNotFoundException if the user does not exist
	 */

	@Override
	public void deleteUser(Long id) {
		if (!userRepository.existsById(id)) {
			throw new ResourceNotFoundException("User not found with id: " + id);
		}
		userRepository.deleteById(id);
	}

	/**
	 * Retrieves a user by email.
	 *
	 * @param email the email of the user
	 * @return a {@link UserResponse} representing the user
	 * @throws ResourceNotFoundException if the user does not exist
	 */

	@Override
	public UserResponse getUserByEmail(String email) {
		User user = userRepository.findByEmail(email);
		if (user == null) {
			throw new ResourceNotFoundException("User", "email", email);
		}
		return mapToResponse(user);
	}

	@Override
	public String updatePasswordIfEmailExists(String email, String newPassword) {
		User user = userRepository.findByEmail(email);
		if (user == null) {
			throw new ResourceNotFoundException("User does not exist. Please sign up.");
		}
		user.setPassword(passwordEncoder.encode(newPassword));
		userRepository.save(user);
		return "Password updated successfully!";
	}

	/**
	 * Maps a {@link User} entity to a {@link UserResponse}.
	 *
	 * @param user the entity to map
	 * @return the mapped response object
	 */

	private UserResponse mapToResponse(User user) {
		UserResponse response = new UserResponse();
		response.setId(user.getId());
		response.setName(user.getName());
		response.setEmail(user.getEmail());
		response.setPhoneNumber(user.getPhoneNumber());
		response.setRole(user.getRole());
		return response;
	}

}
