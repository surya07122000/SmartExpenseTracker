package com.monexel.expensetracker.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


/**
 * Represents a user in the expense tracker system.
 * This entity stores user details such as name, email, password,
 * phone number, and role.
 *
 * <p>Mapped to the database table <b>users</b>.</p>
 *
 * Fields:
 * <ul>
 *   <li><b>id</b> - Unique identifier for the user.</li>
 *   <li><b>name</b> - Full name of the user (cannot be blank, 2–50 characters).</li>
 *   <li><b>email</b> - Email address of the user (must be valid and unique).</li>
 *   <li><b>password</b> - Password for authentication (minimum 6 characters).</li>
 *   <li><b>phoneNumber</b> - User's phone number (must be 10 digits and unique).</li>
 *   <li><b>role</b> - Role assigned to the user (e.g., ADMIN, USER).</li>
 * </ul>
 *
 * Validation:
 * <ul>
 *   <li>Name cannot be blank and must be between 2 and 50 characters.</li>
 *   <li>Email must be valid and cannot be blank.</li>
 *   <li>Password must be at least 6 characters and cannot be blank.</li>
 *   <li>Phone number must be exactly 10 digits.</li>
 * </ul>
 *
 * Relationships:
 * <ul>
 *   <li>This entity can be linked to expenses, income, and categories created by the user.</li>
 * </ul>
 *
 * @author Surya Narayanan G
 * @version 1.0
 */

@Entity
@Table(name = "users")
public class User{

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    @Column(nullable = false)
    private String name;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email cannot be blank")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, message = "Password must be at least 6 characters")
    @Column(nullable = false)
    private String password;
    

    @NotBlank(message = "Phone number cannot be blank")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
    @Column(nullable = false, unique = true)
    private String phoneNumber;
    
    
    private String role;
    
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public User(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
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


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getPhoneNumber() {
		return phoneNumber;
	}


	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

    

}
	


