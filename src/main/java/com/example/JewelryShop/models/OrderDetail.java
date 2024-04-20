package com.example.JewelryShop.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table
public class OrderDetail extends BaseModel {
    @Column(name = "jewelry_item_id")
    private Long jewelry_item_id;

    @Column(name = "total_price")
    private Double total_price;

    @Column(name = "quantity")
    private Integer quantity;
}
