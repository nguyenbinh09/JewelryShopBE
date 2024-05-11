package com.example.JewelryShop.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class CartDetailDTO {
    private Long jewelry_item_id;
    private Double total_price;
    private Integer quantity;
}
