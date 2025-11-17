package com.monexel.expensetracker;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.monexel.expensetracker.entity.User;
import com.monexel.expensetracker.repository.UserRepository;
import com.monexel.expensetracker.service.CustomUserDetailsService;

public class CustomUserDetailsServiceTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private CustomUserDetailsService customUserDetailsService;

	@BeforeEach

	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	private User getSampleUser() {
		User user = new User();
		user.setId(1L);
		user.setEmail("john@example.com");
		user.setPassword("encodedPass");
		user.setRole("USER");
		return user;
	}

	@Test
	void testLoadUserByUsername_Success() {
		User user = getSampleUser();
		when(userRepository.findByEmail("john@example.com")).thenReturn(user);

		UserDetails userDetails = customUserDetailsService.loadUserByUsername("john@example.com");

		assertNotNull(userDetails);
		assertEquals("john@example.com", userDetails.getUsername());
		assertEquals("encodedPass", userDetails.getPassword());
		assertTrue(userDetails.getAuthorities().containsAll(Collections
				.singleton(new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_USER"))));
		verify(userRepository, times(1)).findByEmail("john@example.com");
	}

	@Test
	void testLoadUserByUsername_NotFound() {
		when(userRepository.findByEmail("john@example.com")).thenReturn(null);

		assertThrows(UsernameNotFoundException.class,
				() -> customUserDetailsService.loadUserByUsername("john@example.com"));
		verify(userRepository, times(1)).findByEmail("john@example.com");
	}

}
