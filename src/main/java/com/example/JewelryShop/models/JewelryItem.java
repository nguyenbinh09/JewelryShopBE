package com.example.JewelryShop.models;

import com.fasterxml.jackson.annotation.*;
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
    private String sku_code = "";

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description = "";

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
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

    @OneToMany(mappedBy = "jewelry_item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "jewelry_item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images = new ArrayList<>();

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "quantity")
    private Integer quantity = 0;
}