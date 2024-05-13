package com.example.JewelryShop.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class CartDTO {
    //@NotNull(message = "Items are mandatory")
    private CartDetailDTO[] items;
}
