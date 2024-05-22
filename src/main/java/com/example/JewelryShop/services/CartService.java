package com.example.JewelryShop.services;

import com.example.JewelryShop.dtos.CartDetailDTO;
import com.example.JewelryShop.exceptions.NotFoundException;
import com.example.JewelryShop.models.Cart;
import com.example.JewelryShop.models.CartDetail;
import com.example.JewelryShop.models.JewelryItem;
import com.example.JewelryShop.models.Variant;
import com.example.JewelryShop.repositories.CartDetailRepository;
import com.example.JewelryShop.repositories.CartRepository;
import com.example.JewelryShop.repositories.JewelryItemRepository;
import com.example.JewelryShop.repositories.VariantRepository;
import com.example.JewelryShop.utils.CartesianVariant;
import jakarta.persistence.Access;
import jakarta.transaction.Transactional;
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
    private CartDetailRepository cartDetailRepository;
    @Autowired
    private VariantRepository variantRepository;

    @Transactional
    public ResponseEntity<?> addCartDetailToCart(Long cartId, @Valid CartDetailDTO cartDetailDTO) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new NotFoundException("Cart with id " + cartId + " does not exist"));
        CartDetail cartDetail = cartDetailDTO.toEntity();
        Variant variantItem = variantRepository.findById(cartDetailDTO.getVariant_id()).orElseThrow(() -> new NotFoundException("Variant item with id " + cartDetailDTO.getVariant_id() + " does not exist"));
        cartDetail.setVariant(variantItem);
        cartDetail.setCart(cart);
        cart.getItems().add(cartDetail);
        cartRepository.save(cart);
        return ResponseEntity.ok("Item added to cart successfully");
    }

    public Cart getCartById(Long id) {
        return cartRepository.findById(id).orElseThrow(() -> new NotFoundException("Cart with id " + id + " does not exist"));
    }

    @Transactional
    public ResponseEntity<?> removeCartDetailFromCart(Long cartId, Long cartDetailId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new NotFoundException("Cart with id " + cartId + " does not exist"));
        CartDetail cartDetail = cart.getItems().stream().filter(item -> item.getId().equals(cartDetailId)).findFirst().orElseThrow(() -> new NotFoundException("Cart detail with id " + cartDetailId + " does not exist"));
        cart.getItems().remove(cartDetail);
        cartDetailRepository.delete(cartDetail);
        cartRepository.save(cart);
        return ResponseEntity.ok("Item removed from cart successfully");
    }
}
