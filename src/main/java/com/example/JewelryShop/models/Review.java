package com.example.JewelryShop.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table
public class Review extends BaseModel {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "jewelry_item_id")
    private JewelryItem jewelry_item;

    @Column(name = "text")
    private String text;

    @Column(name = "images")
    private List<String> images = new ArrayList<>();

    @Column(name = "rating")
    private Float rating;
}
