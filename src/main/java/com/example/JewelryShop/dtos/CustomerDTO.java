package com.example.JewelryShop.dtos;

import com.example.JewelryShop.models.WishlistDetail;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
public class CustomerDTO {
    private Long user_id;
    private List<WishlistDetail> wishlist;
    private Long cart_id;
    private Long[] order_history;
}
