package com.example.JewelryShop.dtos;

import com.example.JewelryShop.models.Variant;
import lombok.Data;

import java.util.List;

@Data
public class VariantUpdateDTO {
    private Integer price;
    private Integer quantity;
}
