package com.example.JewelryShop.controllers;

import com.example.JewelryShop.dtos.CartDetailDTO;
import com.example.JewelryShop.exceptions.InternalServerErrorException;
import com.example.JewelryShop.exceptions.NotFoundException;
import com.example.JewelryShop.models.Cart;
import com.example.JewelryShop.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(path = "api/v1/cart")
@Validated
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping("/{id}")
    public Cart getCartById(@PathVariable Long id) {
        try {
            return cartService.getCartById(id);
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @PostMapping("/{cartId}/addCartDetail")
    public ResponseEntity<?> addCartDetailToCart(@PathVariable Long cartId, @RequestBody CartDetailDTO cartDetailDTO) {
        try {
            return cartService.addCartDetailToCart(cartId, cartDetailDTO);
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }


}
