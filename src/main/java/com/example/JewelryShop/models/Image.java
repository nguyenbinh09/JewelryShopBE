package com.example.JewelryShop.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table
public class Image extends BaseModel {
    @Column(name = "url")
    private String url;

    @ManyToOne
    @JoinColumn(name = "jewelry_item_id")
    private JewelryItem jewelry_item;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;

    @OneToOne(mappedBy = "avatar")
    private User user;

    public Image() {
    }

    public Image(String url) {
        this.url = url;
    }
}
