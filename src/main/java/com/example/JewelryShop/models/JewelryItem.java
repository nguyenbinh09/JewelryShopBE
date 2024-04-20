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
public class JewelryItem extends BaseModel {
    @Column(name = "sku_code", unique = true)
    @NotNull(message = "SKU Code is mandatory")
    private String sku_code;

    @Column(name = "name")
    @NotNull(message = "Name is mandatory")
    private String name;

    @Column(name = "description")
    private String description = "";

    @Column(name = "category_id")
    @NotNull(message = "Category is mandatory")
    private Long category_id;

    @Column(name = "material")
    @NotNull(message = "Material is mandatory")
    private String material;

    @Column(name = "color")
    private String color;

    @Column(name = "metal_color")
    private String metal_color;

    @Column(name = "stone_color")
    private String stone_color;

    @Column(name = "image")
    private String image = "https://i.pinimg.com/originals/c6/e5/65/c6e56503cfdd87da299f72dc416023d4.jpg";

    @Column(name = "reviews")
    private Long[] reviews;

    @Column(name = "price")
    @NotNull(message = "Price is mandatory")
    private Double price;

    @Column(name = "quantity")
    private Integer quantity = 0;
}