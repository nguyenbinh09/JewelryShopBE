package com.example.JewelryShop.dtos;

import jakarta.persistence.Column;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class JewelryItemDTO extends BaseDTO {
    private String sku_code;
    private String name;
    private String description;
    private Long category_id;
    private String material;
    private String color;
    private String metal_color;
    private String stone_color;
    private String image;
    private Long[] reviews;
    private Double price;
    private Integer quantity;
}
