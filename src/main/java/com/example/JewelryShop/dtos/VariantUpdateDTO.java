package com.example.JewelryShop.dtos;

import com.example.JewelryShop.models.Variant;
import lombok.Data;

import java.util.List;

@Data
public class VariantUpdateDTO {
    private Double price;
    private Integer quantity;

    public Variant toEntity(Variant variant) {
        if (this.price != null)
            variant.setPrice(this.price);
        if (this.quantity != null)
            variant.setQuantity(this.quantity);
        return variant;
    }
}
