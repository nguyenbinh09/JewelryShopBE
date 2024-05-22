package com.example.JewelryShop.controllers;

import com.example.JewelryShop.models.WishlistDetail;
import com.example.JewelryShop.services.WishlistDetailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(path = "api/v1/wishlist_detail")
public class WishlistDetailController {
    @Autowired
    private WishlistDetailService wishlistDetailService;

    @GetMapping("/find_all_by_customer_id/{customer_id}")
    public List<WishlistDetail> getWishlistDetailsByCustomerId(@PathVariable("customer_id") Long customerId) {
        return wishlistDetailService.getWishlistDetailsByCustomerId(customerId);
    }
}
