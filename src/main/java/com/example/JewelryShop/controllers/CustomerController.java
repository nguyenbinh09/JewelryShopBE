package com.example.JewelryShop.controllers;

import com.example.JewelryShop.exceptions.InternalServerErrorException;
import com.example.JewelryShop.exceptions.NotFoundException;
import com.example.JewelryShop.models.Customer;
import com.example.JewelryShop.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "api/v1/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping
    public List<Customer> getCustomers() {
        try {
            return customerService.getCustomers();
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @GetMapping("/byUserId/{user_id}")
    public Customer getCustomerByUserId(Long user_id) {
        try {
            return customerService.getCustomerByUserId(user_id);
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Customer getCustomerById(Long id) {
        try {
            return customerService.getCustomerById(id);
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @PutMapping("/{id}/add_item_to_wishlist/{variant_id}")
    public ResponseEntity<?> addWishlistDetailToWishlist(@PathVariable("id") Long customerId, @PathVariable("variant_id") Long variantId) {
        try {
            return customerService.addWishlistDetailToWishlist(customerId, variantId);
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @PutMapping("/{id}/remove_item_from_wishlist/{wishlist_detail_id}")
    public ResponseEntity<?> removeItemFromWishlist(@PathVariable("id") Long customerId, @PathVariable("wishlist_detail_id") Long wishlistDetailId) {
        try {
            return customerService.removeItemFromWishlist(customerId, wishlistDetailId);
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }
}
