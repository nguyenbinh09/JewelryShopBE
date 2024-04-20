package com.example.JewelryShop.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table
public class Customer extends BaseModel {
    @Column(name = "user_id", unique = true)
    private Long user_id;

    @Column(name = "wish_list")
    private Long[] wish_list;

    @Column(name = "cart_id")
    private Long cart_id;

    @Column(name = "order_history")
    private Long[] order_history;
}
