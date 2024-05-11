package com.example.JewelryShop.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class ReviewDTO {
    private Long user_id;
    private Long jewelry_item_id;
    private String text;
    private String[] images;
    private Float rating;
}
