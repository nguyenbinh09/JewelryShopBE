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
    @NotNull(message = "Purchaser is mandatory")
    private Long purchaser;
    @NotNull(message = "Total price is mandatory")
    private Double total_price;
    @NotNull(message = "Amount is mandatory")
    private Double amount;
    @NotNull(message = "Shipping contact is mandatory")
    private ContactDTO shipping_contact;
    @NotNull(message = "Payment method is mandatory")
    private Long payment_method;

    public Order toEntity() {
        Order order = new Order();
        order.setTotal_price(total_price);
        order.setAmount(amount);
        return order;
    }
}
