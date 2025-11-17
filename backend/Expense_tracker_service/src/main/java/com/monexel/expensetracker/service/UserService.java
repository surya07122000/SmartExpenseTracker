package com.monexel.expensetracker.service;

import java.util.List;

import com.monexel.expensetracker.request.UserRequest;
import com.monexel.expensetracker.response.UserResponse;

public interface UserService {

	UserResponse createUser(UserRequest request);

	UserResponse getUserById(Long id);

	List<UserResponse> getAllUsers();

	UserResponse updateUser(Long id, UserRequest request);

	void deleteUser(Long id);
	UserResponse getUserByEmail(String email);
	String updatePasswordIfEmailExists(String email, String newPassword);

}
