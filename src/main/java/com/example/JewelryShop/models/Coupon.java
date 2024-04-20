package com.example.JewelryShop.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table
public class Coupon extends BaseModel {
    @Column(name = "code", unique = true)
    private String code;

    @Column(name = "discount_percentage")
    private Double discount_percentage;

    @Column(name = "expiration_date")
    private Date expiration_date;

    @Column(name = "applicable_items")
    private Long[] applicable_items;
}
