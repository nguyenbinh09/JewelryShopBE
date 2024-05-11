package com.example.JewelryShop.dtos;

import com.example.JewelryShop.models.Category;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class CategoryDTO {
    @NotNull(message = "Name is mandatory")
    private String name;
    @NotNull(message = "Code is mandatory")
    private String code;

    public Category toEntity() {
        Category category = new Category();
        category.setName(this.getName());
        category.setCode(this.getCode());
        return category;
    }
};
