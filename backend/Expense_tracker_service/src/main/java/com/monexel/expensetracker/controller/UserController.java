package com.monexel.expensetracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.monexel.expensetracker.jwtutils.JwtUtils;
import com.monexel.expensetracker.request.ForgotPasswordRequest;
import com.monexel.expensetracker.request.LoginRequest;
import com.monexel.expensetracker.request.UserRequest;
import com.monexel.expensetracker.response.LoginResponse;
import com.monexel.expensetracker.response.UserResponse;
import com.monexel.expensetracker.service.UserService;

import jakarta.validation.Valid;

/**
 * REST controller for managing user operations including registration, profile
 * management, authentication (login/logout), and password reset. Provides
 * endpoints for CRUD operations on users and authentication using JWT.
 * @author Surya Narayanan G
 * @version 1.0
 */

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtUtils jwtUtils;

	/**
	 * Creates a new user account.
	 *
	 * @param request the {@link UserRequest} containing user details
	 * @return ResponseEntity containing the created {@link UserResponse} and HTTP
	 *         status 201 (Created)
	 *
	 *         Example:
	 * 
	 *         <pre>
	 *         POST / api / users / createUser
	 *         </pre>
	 */

	@PostMapping("/createUser")
	public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserRequest request) {
		return new ResponseEntity<>(userService.createUser(request), HttpStatus.CREATED);
	}

	/**
	 * Retrieves a user by ID.
	 *
	 * @param id the ID of the user
	 * @return ResponseEntity containing the {@link UserResponse}
	 * @throws ResourceNotFoundException if the user with given ID does not exist
	 *
	 *                                   Example:
	 * 
	 *                                   <pre>
	 *                                   GET / api / users / getUserById / 1
	 *                                   </pre>
	 */

	@GetMapping("/getUserById/{id}")
	public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
		return ResponseEntity.ok(userService.getUserById(id));
	}

	/**
	 * Retrieves all users.
	 *
	 * @return ResponseEntity containing a list of {@link UserResponse}
	 *
	 *         Example:
	 * 
	 *         <pre>
	 *         GET / api / users / getAllUsers
	 *         </pre>
	 */

	@GetMapping("/getAllUsers")
	public ResponseEntity<List<UserResponse>> getAllUsers() {
		return ResponseEntity.ok(userService.getAllUsers());
	}

	/**
	 * Updates an existing user by ID.
	 *
	 * @param id      the ID of the user to update
	 * @param request the {@link UserRequest} with updated details
	 * @return ResponseEntity containing the updated {@link UserResponse}
	 * @throws ResourceNotFoundException if the user with given ID does not exist
	 *
	 *                                   Example:
	 * 
	 *                                   <pre>
	 *                                   PUT / api / users / updateUser / 1
	 *                                   </pre>
	 */

	@PutMapping("/updateUser/{id}")
	public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @RequestBody @Valid UserRequest request) {
		return ResponseEntity.ok(userService.updateUser(id, request));
	}

	/**
	 * Deletes a user by ID.
	 *
	 * @param id the ID of the user to delete
	 * @return ResponseEntity with HTTP status 204 (No Content)
	 * @throws ResourceNotFoundException if the user with given ID does not exist
	 *
	 *                                   Example:
	 * 
	 *                                   <pre>
	 *                                   DELETE / api / users / deleteUser / 1
	 *                                   </pre>
	 */

	@DeleteMapping("/deleteUser/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);
		return ResponseEntity.noContent().build();
	}

	/**
	 * Retrieves user profile by email.
	 *
	 * @param email the email of the user
	 * @return ResponseEntity containing the {@link UserResponse}
	 *
	 *         Example:
	 * 
	 *         <pre>
	 * GET /api/users/profile?email=john@example.com
	 *         </pre>
	 */

	@GetMapping("/profile")
	public ResponseEntity<UserResponse> getUserProfile(@RequestParam String email) {
		UserResponse userResponse = userService.getUserByEmail(email);
		return ResponseEntity.ok(userResponse);
	}

	/**
	 * Authenticates a user and generates a JWT token.
	 *
	 * @param loginRequest the {@link LoginRequest} containing email and password
	 * @return ResponseEntity containing {@link LoginResponse} with JWT token and
	 *         user details, or HTTP status 401 (Unauthorized) if authentication
	 *         fails
	 *
	 *         Example:
	 * 
	 *         <pre>
	 *         POST / api / users / signin
	 *         </pre>
	 */

	@PostMapping("/signin")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {

		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
			UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
			String jwt = jwtUtils.generateTokenFromUsername(userDetails.getUsername());
			UserResponse user = userService.getUserByEmail(loginRequest.getEmail());
			ResponseCookie cookie = ResponseCookie.from("jwt", jwt).httpOnly(true).secure(true).path("/").maxAge(3600)
					.sameSite("Strict").build();
			HttpHeaders headers = new HttpHeaders();
			headers.add(HttpHeaders.SET_COOKIE, cookie.toString());
			LoginResponse loginResponse = new LoginResponse();
			loginResponse.setEmail(userDetails.getUsername());
			loginResponse.setJwt(jwt);
			loginResponse.setId(user.getId());
			return new ResponseEntity<>(loginResponse, headers, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("Invalid username or password", HttpStatus.UNAUTHORIZED);
		}
	}

	/**
	 * Logs out the user by clearing the JWT cookie.
	 *
	 * @return ResponseEntity with HTTP status 200 (OK) and a success message
	 *
	 *         Example:
	 * 
	 *         <pre>
	 *         GET / api / users / signout
	 *         </pre>
	 */

	@GetMapping("/signout")
	public ResponseEntity<String> logout() {

		ResponseCookie deleteCookie = ResponseCookie.from("jwt", "").httpOnly(true).secure(true).path("/").maxAge(0)
				.sameSite("Strict").build();
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.SET_COOKIE, deleteCookie.toString());
		return new ResponseEntity<String>("Logged out successfully", headers, HttpStatus.OK);
	}

	/**
	 * Updates the password for a user if the email exists.
	 *
	 * @param request the {@link ForgotPasswordRequest} containing email and new
	 *                password
	 * @return ResponseEntity containing a success message
	 * @throws ResourceNotFoundException if the email does not exist
	 *
	 *                                  Example:
	 * 
	 *                                   <pre>
	 *                                   PUT / api / users / forgot - password
	 *                                   </pre>
	 */

	@PutMapping("/forgot-password")
	public ResponseEntity<String> forgotPassword(@RequestBody @Valid ForgotPasswordRequest request) {
		String message = userService.updatePasswordIfEmailExists(request.getEmail(), request.getNewPassword());
		return ResponseEntity.ok(message);
	}

}
