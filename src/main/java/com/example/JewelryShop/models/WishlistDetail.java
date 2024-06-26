package com.example.JewelryShop.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table
public class WishlistDetail extends BaseModel {
    @ManyToOne
    @JoinColumn(name = "jewelry_item_id")
    private JewelryItem jewelryItem;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "wishlist_id")
    private Customer customer;
}
