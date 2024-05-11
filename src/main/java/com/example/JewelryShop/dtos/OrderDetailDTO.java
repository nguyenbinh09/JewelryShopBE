package com.example.JewelryShop.dtos;

import com.example.JewelryShop.models.OrderDetail;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class OrderDetailDTO {
    private Long jewelry_item_id;
    private Double total_price;
    private Integer quantity;

    public OrderDetail toEntity() {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setTotal_price(total_price);
        orderDetail.setQuantity(quantity);
        return orderDetail;
    }
}
