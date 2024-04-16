package com.example.JewelryShop.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table
public class JewelryItem extends BaseModel{
    @NotNull(message = "SKU Code is mandatory")
    private String sku_code;

    @NotNull(message = "Name is mandatory")
    private String name;

    private String description = "";

    @NotNull(message = "Category is mandatory")
    private Long category;

    @NotNull(message = "Material is mandatory")
    private String material;

    private String image = "https://i.pinimg.com/originals/c6/e5/65/c6e56503cfdd87da299f72dc416023d4.jpg";

    private Long[] reviews;

    @NotNull(message = "Price is mandatory")
    private Double price;

    private Integer quantity = 0;
}
