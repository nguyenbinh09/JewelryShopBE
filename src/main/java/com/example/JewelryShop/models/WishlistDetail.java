package com.example.JewelryShop.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table
public class WishlistDetail extends BaseModel {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "jewelry_item_id")
    private JewelryItem jewelry_item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wishlist_id")
    private Customer customer;
}
