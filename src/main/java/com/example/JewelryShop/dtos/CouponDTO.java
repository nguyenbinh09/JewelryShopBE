package com.example.JewelryShop.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
public class CouponDTO {
    private String code;
    private Double discount_percentage;
    private Date expiration_date;
    private Long[] applicable_items;
}
