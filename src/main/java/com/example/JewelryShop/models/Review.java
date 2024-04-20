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
public class Review extends BaseModel {
    @Column(name = "user_id", unique = true)
    private Long user_id;

    @Column(name = "jewelry_item_id", unique = true)
    private Long jewelry_item_id;

    @Column(name = "text")
    private String text;

    @Column(name = "images")
    private String[] images;

    @Column(name = "rating")
    private Float rating;
}
