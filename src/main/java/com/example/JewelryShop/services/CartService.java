package com.example.JewelryShop.services;

import com.example.JewelryShop.dtos.CartDetailDTO;
import com.example.JewelryShop.exceptions.NotFoundException;
import com.example.JewelryShop.models.Cart;
import com.example.JewelryShop.models.CartDetail;
import com.example.JewelryShop.models.JewelryItem;
import com.example.JewelryShop.repositories.CartRepository;
import com.example.JewelryShop.repositories.JewelryItemRepository;
import jakarta.persistence.Access;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private JewelryItemRepository jewelryItemRepository;

    public ResponseEntity<?> addCartDetailToCart(Long cartId, @Valid CartDetailDTO cartDetailDTO) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new NotFoundException("Cart with id " + cartId + " does not exist"));
        CartDetail cartDetail = cartDetailDTO.toEntity();
        JewelryItem jewelryItem = jewelryItemRepository.findById(cartDetailDTO.getJewelry_item_id()).orElseThrow(() -> new NotFoundException("Jewelry item with id " + cartDetailDTO.getJewelry_item_id() + " does not exist"));
        cartDetail.setJewelry_item(jewelryItem);
        cart.getItems().add(cartDetail);
        cartRepository.save(cart);
        return ResponseEntity.ok("Item added to cart successfully");
    }

    public Cart getCartById(Long id) {
        return cartRepository.findById(id).orElseThrow(() -> new NotFoundException("Cart with id " + id + " does not exist"));
    }
}
