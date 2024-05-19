package com.example.JewelryShop.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "jewelry_item_id")
    private JewelryItem jewelry_item;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;

    @JsonIgnore
    @OneToOne(mappedBy = "avatar")
    private User user;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "variant_id")
    private Variant variant;

    public Image() {
    }

    public Image(String url) {
        this.url = url;
    }
}
