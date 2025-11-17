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

    @PostMapping("/createUser")
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserRequest request) {
        return new ResponseEntity<>(userService.createUser(request), HttpStatus.CREATED);
    }

    @GetMapping("/getUserById/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @RequestBody @Valid UserRequest request) {
        return ResponseEntity.ok(userService.updateUser(id, request));
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/profile")
    public ResponseEntity<UserResponse> getUserProfile(@RequestParam String email) {
    UserResponse userResponse = userService.getUserByEmail(email);
    return ResponseEntity.ok(userResponse);
    }

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
 
	@GetMapping("/signout")
	public ResponseEntity<String> logout() {
 
		ResponseCookie deleteCookie = ResponseCookie.from("jwt", "").httpOnly(true).secure(true).path("/").maxAge(0)
				.sameSite("Strict").build();
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.SET_COOKIE, deleteCookie.toString());
		return new ResponseEntity<String>("Logged out successfully", headers, HttpStatus.OK);
	}



	@PutMapping("/forgot-password")
	public ResponseEntity<String> forgotPassword(@RequestBody @Valid ForgotPasswordRequest request) {
    String message = userService.updatePasswordIfEmailExists(request.getEmail(), request.getNewPassword());
    return ResponseEntity.ok(message);
	}





}
