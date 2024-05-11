package com.example.JewelryShop.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table
public class JewelryItem extends BaseModel {
    @Column(name = "sku_code", unique = true, nullable = false)
    private String sku_code;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description = "";

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @Column(name = "material", nullable = false)
    private String material;

    @Column(name = "color", nullable = false)
    private String color;

    @Column(name = "metal_color")
    private String metal_color;

    @Column(name = "stone_color")
    private String stone_color;

    @Column(name = "image")
    private String image = "https://i.pinimg.com/originals/c6/e5/65/c6e56503cfdd87da299f72dc416023d4.jpg";

    @OneToMany(mappedBy = "jewelry_item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "quantity")
    private Integer quantity = 0;
}