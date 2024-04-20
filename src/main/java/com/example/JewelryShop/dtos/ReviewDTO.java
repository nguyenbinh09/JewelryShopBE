package com.example.JewelryShop.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ReviewDTO extends BaseDTO {
    private Long user_id;
    private Long jewelry_item_id;
    private String text;
    private String[] images;
    private Float rating;
}
