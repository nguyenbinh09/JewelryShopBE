package com.example.JewelryShop.services;

import com.example.JewelryShop.dtos.CategoryDTO;
import com.example.JewelryShop.exceptions.BadRequestException;
import com.example.JewelryShop.exceptions.NotFoundException;
import com.example.JewelryShop.models.Category;
import com.example.JewelryShop.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
@Validated
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<Category> getCategories() {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty())
            throw new NotFoundException("Could not found any categories");
        return categories;
    }

    public Category getCategoryById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isEmpty())
            throw new NotFoundException("Could not find category with id " + id);
        return category.get();
    }

    public ResponseEntity<?> addNewCategory(CategoryDTO categoryDTO) {
        Category category = categoryDTO.toEntity();
        categoryRepository.save(category);
        return ResponseEntity.ok("Category created successfully");
    }

    public ResponseEntity<?> updateCategory(Long id, CategoryDTO categoryDTO) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isEmpty())
            throw new NotFoundException("Could not find category with id " + id);
        Category categoryToUpdate = category.get();
        categoryToUpdate.setName(categoryDTO.getName());
        categoryRepository.save(categoryToUpdate);
        return ResponseEntity.ok("Category updated successfully");
    }
}
