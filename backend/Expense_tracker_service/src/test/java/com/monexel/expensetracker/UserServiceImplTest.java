package com.monexel.expensetracker;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import java.util.List;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.monexel.expensetracker.entity.User;
import com.monexel.expensetracker.exception.ResourceNotFoundException;
import com.monexel.expensetracker.repository.UserRepository;
import com.monexel.expensetracker.request.UserRequest;
import com.monexel.expensetracker.response.UserResponse;
import com.monexel.expensetracker.service.UserServiceImpl;


public class UserServiceImplTest {
	@Mock
    private UserRepository userRepository;

	@Mock
	private PasswordEncoder  passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private User getSampleUser() {
        User user = new User();
        user.setId(1L);
        user.setName("John");
        user.setEmail("john@example.com");
        user.setPassword("encodedPass");
        user.setPhoneNumber("1234567890");
        user.setRole("USER");
        return user;
    }
    
    @Test
    void testCreateUser() {
        UserRequest request = new UserRequest();
        request.setName("John");
        request.setEmail("john@example.com");
        request.setPassword("password");
        request.setPhoneNumber("1234567890");
        request.setRole("USER");

        when(passwordEncoder.encode("password")).thenReturn("encodedPass");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User u = invocation.getArgument(0);
            u.setId(1L);
            return u;
        });

        UserResponse response = userService.createUser(request);

        assertNotNull(response);
        assertEquals("John", response.getName());
        assertEquals("john@example.com", response.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
    }
    
    @Test
    void testGetUserById_Success() {
        User user = getSampleUser();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserResponse response = userService.getUserById(1L);

        assertEquals("John", response.getName());
        assertEquals("john@example.com", response.getEmail());
    }

    @Test
    void testGetUserById_NotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(1L));
    }
    
    @Test
    void testGetAllUsers() {
        User user1 = getSampleUser();
        User user2 = new User();
        user2.setId(2L);
        user2.setName("Jane");
        user2.setEmail("jane@example.com");
        user2.setPassword("encodedPass");
        user2.setPhoneNumber("9876543210");
        user2.setRole("ADMIN");

        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        List<UserResponse> responses = userService.getAllUsers();

        assertEquals(2, responses.size());
        assertEquals("John", responses.get(0).getName());
        assertEquals("Jane", responses.get(1).getName());
    }
    @Test
    void testUpdateUser_Success() {
        User existingUser = getSampleUser();
        UserRequest request = new UserRequest();
        request.setName("Updated");
        request.setEmail("updated@example.com");
        request.setPassword("newPass");
        request.setPhoneNumber("111222333");
        request.setRole("ADMIN");

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.encode("newPass")).thenReturn("encodedNewPass");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UserResponse response = userService.updateUser(1L, request);

        assertEquals("Updated", response.getName());
        assertEquals("updated@example.com", response.getEmail());
        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    void testUpdateUser_NotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        UserRequest request = new UserRequest();
        assertThrows(ResourceNotFoundException.class, () -> userService.updateUser(1L, request));
    }
    @Test
    void testDeleteUser_Success() {
        when(userRepository.existsById(1L)).thenReturn(true);
        doNothing().when(userRepository).deleteById(1L);

        userService.deleteUser(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteUser_NotFound() {
        when(userRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> userService.deleteUser(1L));
    }
    
    @Test
    void testGetUserByEmail_Success() {
        User user = getSampleUser();
        when(userRepository.findByEmail("john@example.com")).thenReturn(user);

        UserResponse response = userService.getUserByEmail("john@example.com");

        assertEquals("John", response.getName());
    }

    @Test
    void testGetUserByEmail_NotFound() {
        when(userRepository.findByEmail("john@example.com")).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> userService.getUserByEmail("john@example.com"));
    }
    @Test
    void testUpdatePasswordIfEmailExists_Success() {
        User user = getSampleUser();
        when(userRepository.findByEmail("john@example.com")).thenReturn(user);
        when(passwordEncoder.encode("newPass")).thenReturn("encodedNewPass");
        when(userRepository.save(user)).thenReturn(user);

        String result = userService.updatePasswordIfEmailExists("john@example.com", "newPass");

        assertEquals("Password updated successfully!", result);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testUpdatePasswordIfEmailExists_NotFound() {
        when(userRepository.findByEmail("john@example.com")).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> userService.updatePasswordIfEmailExists("john@example.com", "newPass"));
    }


}
