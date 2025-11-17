package com.monexel.expensetracker;

import com.monexel.expensetracker.entity.Category;
import com.monexel.expensetracker.entity.User;
import com.monexel.expensetracker.exception.ResourceNotFoundException;
import com.monexel.expensetracker.repository.CategoryRepository;
import com.monexel.expensetracker.repository.UserRepository;
import com.monexel.expensetracker.request.CategoryRequest;
import com.monexel.expensetracker.response.CategoryResponse;
import com.monexel.expensetracker.service.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {


@Mock
    private CategoryRepository categoryRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private User user;
    private Category category;

	
	@BeforeEach
	void setUp() {
		user = new User();
		user.setId(1L);

		category = new Category();
		category.setId(10L);
		category.setName("Food");
		category.setDescription("Food related expenses");
		category.setCreatedByUser(user);
	}

	@Test
	void testAddCategory_WithUser_Success() {
		CategoryRequest request = new CategoryRequest();
		request.setName("Food");
		request.setDescription("Food related expenses");
		request.setUserId(1L);

		when(userRepository.findById(1L)).thenReturn(Optional.of(user));
		when(categoryRepository.save(any(Category.class))).thenReturn(category);

		CategoryResponse response = categoryService.addCategory(request);

		assertNotNull(response);
		assertEquals("Food", response.getName());
		verify(categoryRepository, times(1)).save(any(Category.class));
	}

	@Test
	void testAddCategory_UserNotFound() {
		CategoryRequest request = new CategoryRequest();
		request.setName("Travel");
		request.setUserId(99L);

		when(userRepository.findById(99L)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> categoryService.addCategory(request));
	}

	@Test
	void testAddCategory_WithoutUser_Success() {
		CategoryRequest request = new CategoryRequest();
		request.setName("General");
		request.setDescription("General expenses");

		when(categoryRepository.save(any(Category.class))).thenReturn(category);

		CategoryResponse response = categoryService.addCategory(request);

		assertEquals("Food", response.getName()); // Mock returns category object
	}

	@Test
	void testUpdateCategory_Success() {
		CategoryRequest request = new CategoryRequest();
		request.setName("Food");
		request.setDescription("Updated description");

		when(categoryRepository.findById(10L)).thenReturn(Optional.of(category));
		when(categoryRepository.save(any(Category.class))).thenReturn(category);

		CategoryResponse response = categoryService.updateCategory(10L, request);

		assertEquals("Food", response.getName()); // Mock returns same object
		verify(categoryRepository, times(1)).save(category);
	}

	@Test
	void testUpdateCategory_NotFound() {
		CategoryRequest request = new CategoryRequest();
		when(categoryRepository.findById(99L)).thenReturn(Optional.empty());
		assertThrows(ResourceNotFoundException.class, () -> categoryService.updateCategory(99L, request));
	}

	@Test
	void testDeleteCategory_Success() {
		when(categoryRepository.existsById(10L)).thenReturn(true);
		categoryService.deleteCategory(10L);
		verify(categoryRepository, times(1)).deleteById(10L);
	}

	@Test
	void testDeleteCategory_NotFound() {
		when(categoryRepository.existsById(99L)).thenReturn(false);
		assertThrows(ResourceNotFoundException.class, () -> categoryService.deleteCategory(99L));
	}

	@Test
	void testGetCategoryById_Success() {
		when(categoryRepository.findById(10L)).thenReturn(Optional.of(category));
		CategoryResponse response = categoryService.getCategoryById(10L);
		assertEquals("Food", response.getName());
	}

	@Test
	void testGetAllCategories() {
		when(categoryRepository.findAll()).thenReturn(List.of(category));
		List<CategoryResponse> responses = categoryService.getAllCategories();
		assertEquals(1, responses.size());
	}

	@Test
	void testGetCustomCategoriesByUser() {
		when(categoryRepository.findByCreatedByUserId(1L)).thenReturn(List.of(category));
		List<CategoryResponse> responses = categoryService.getCustomCategoriesByUser(1L);
		assertEquals(1, responses.size());
	}
}