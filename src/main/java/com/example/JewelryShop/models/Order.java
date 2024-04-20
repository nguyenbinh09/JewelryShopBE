package com.example.JewelryShop.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table
public class Order extends BaseModel {
    @Column(name = "ordered_items")
    private Long[] ordered_items;

    @Column(name = "purchaser", unique = true)
    private Long purchaser;

    @Column(name = "completed_time")
    private Date completed_time;

    @Column(name = "status")
    private String status;

    @Column(name = "total_price")
    private Double total_price;

    @Column(name = "price")
    private Double price;

    @Column(name = "shipping_contact")
    private Long shipping_contact;

    @Column(name = "payment_method")
    private Long payment_method;
}
