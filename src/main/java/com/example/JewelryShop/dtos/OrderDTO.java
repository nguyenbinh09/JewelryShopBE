package com.example.JewelryShop.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class OrderDTO extends BaseDTO {
    private Long[] ordered_items;
    private Long purchaser;
    private Date completed_time;
    private String status;
    private Double total_price;
    private Double price;
    private Long shipping_contact;
    private Long payment_method;
    private Long review;
}
