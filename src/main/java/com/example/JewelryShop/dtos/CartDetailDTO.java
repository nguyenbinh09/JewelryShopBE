package com.example.JewelryShop.dtos;

import com.example.JewelryShop.models.CartDetail;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class CartDetailDTO {
    private Long jewelry_item_id;
    private Double total_price;
    private Integer quantity;

    public CartDetail toEntity() {
        CartDetail cartDetail = new CartDetail();
        cartDetail.setTotal_price(total_price);
        cartDetail.setQuantity(quantity);
        return cartDetail;
    }
}
