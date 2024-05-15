package com.example.JewelryShop.dtos;

import com.example.JewelryShop.models.Category;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class CategoryDTO {
    @NotNull(message = "Name is mandatory")
    private String name;

    public Category toEntity() {
        Category category = new Category();
        category.setName(this.getName());
        return category;
    }
};
