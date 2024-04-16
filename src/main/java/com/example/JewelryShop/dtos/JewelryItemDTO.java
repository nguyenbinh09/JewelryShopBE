package com.example.JewelryShop.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class JewelryItemDTO extends  BaseDTO{
    private String sku_code;
    private String name;
    private String description;
    private Long category;
    private String material;
    private String image;
    private Long[] reviews;
    private Double price;
    private Integer quantity;
}
