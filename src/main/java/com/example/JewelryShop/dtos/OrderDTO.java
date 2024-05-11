package com.example.JewelryShop.dtos;

import com.example.JewelryShop.models.Order;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
public class OrderDTO {
    @NotNull(message = "Ordered items are mandatory")
    private OrderDetailDTO[] ordered_items;
    //@NotNull(message = "Purchaser is mandatory")
    private Long purchaser;
    private Double total_price;
    private Double amount;
    private ContactDTO shipping_contact;
    private Long payment_method;

    public Order toEntity() {
        Order order = new Order();
        order.setTotal_price(total_price);
        order.setAmount(amount);
        return order;
    }
}
