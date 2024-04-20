package com.example.JewelryShop.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class OrderDetailDTO extends BaseDTO {
    private Long jewelry_item_id;
    private Double total_price;
    private Integer quantity;
}
