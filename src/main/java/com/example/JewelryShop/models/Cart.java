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
public class Cart extends BaseModel {
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartDetail> items = new ArrayList<>();

    @OneToOne(mappedBy = "cart")
    private Customer customer;
}
