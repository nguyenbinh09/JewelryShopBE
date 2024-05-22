package com.example.JewelryShop.dtos;

import com.example.JewelryShop.models.Review;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class ReviewDTO {
    private Long customer_id;
    private Long jewelry_item_id;
    private String text;
    private Float rating;

    public Review toEntity() {
        Review review = new Review();
        review.setText(this.text);
        review.setRating(this.rating);
        return review;
    }
}
