package com.example.JewelryShop.services;

import com.example.JewelryShop.dtos.CategoryDTO;
import com.example.JewelryShop.exceptions.NotFoundException;
import com.example.JewelryShop.models.Category;
import com.example.JewelryShop.repositories.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    void getCategoryById_WhenCategoryExists_ReturnCategory() {
        // Arrange
        Long categoryId = 1L;
        Category category = new Category();
        category.setId(categoryId);
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        // Act
        Category result = categoryService.getCategoryById(categoryId);

        // Assert
        assertEquals(categoryId, result.getId());
    }

    @Test
    void getCategoryById_WhenCategoryDoesNotExist_ThrowNotFoundException() {
        // Arrange
        Long categoryId = 1L;
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> categoryService.getCategoryById(categoryId));
    }

    @Test
    void addNewCategory_ReturnOkResponse() {
        // Arrange
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("Test Category");

        // Mocking the categoryRepository.save() method
        when(categoryRepository.save(any(Category.class))).thenAnswer(invocation -> {
            Category savedCategory = invocation.getArgument(0);
            savedCategory.setId(1L); // Simulating that the category has been saved with an ID
            return savedCategory;
        });

        // Act
        ResponseEntity<?> response = categoryService.addNewCategory(categoryDTO);

        // Assert
        assertEquals(ResponseEntity.ok("Category created successfully"), response);
        verify(categoryRepository, times(2)).save(any()); // verify save is called once
    }

    @Test
    void updateCategory_WhenCategoryExists_ReturnOkResponse() {
        // Arrange
        Long categoryId = 1L;
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("Updated Category");

        Category category = new Category();
        category.setId(categoryId);
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        // Act
        ResponseEntity<?> response = categoryService.updateCategory(categoryId, categoryDTO);

        // Assert
        assertEquals(ResponseEntity.ok("Category updated successfully"), response);
        assertEquals(categoryDTO.getName(), category.getName()); // verify name is updated
        verify(categoryRepository, times(1)).save(category); // verify save is called once with updated category
    }

    @Test
    void updateCategory_WhenCategoryDoesNotExist_ThrowNotFoundException() {
        // Arrange
        Long categoryId = 1L;
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("Updated Category");

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> categoryService.updateCategory(categoryId, categoryDTO));
    }
}

