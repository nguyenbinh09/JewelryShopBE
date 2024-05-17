//package com.example.JewelryShop.models;
//
//import jakarta.persistence.*;
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//@Data
//@Table
//@EqualsAndHashCode(callSuper = true)
//public class Variant extends BaseModel {
//    private String variantType; // e.g., Size, Color
//    private String variantValue; // e.g., Medium, Red
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "jewelry_item_id")
//    private JewelryItem jewelryItem;
//
//    @OneToMany(mappedBy = "variant", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Image> images = new ArrayList<>();
//
//    @Column(name = "price", nullable = false)
//    private Double price;
//}
