package com.example.JewelryShop.services;

import com.example.JewelryShop.dtos.CartDetailDTO;
import com.example.JewelryShop.models.Cart;
import com.example.JewelryShop.models.CartDetail;
import com.example.JewelryShop.models.Variant;
import com.example.JewelryShop.repositories.CartDetailRepository;
import com.example.JewelryShop.repositories.CartRepository;
import com.example.JewelryShop.repositories.VariantRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private VariantRepository variantRepository;

    @Mock
    private CartDetailRepository cartDetailRepository;

    @InjectMocks
    private CartService cartService;


    @Test
    void addCartDetailToCart_ValidInput_ReturnOkResponse() {
        // Arrange
        Long cartId = 1L;
        CartDetailDTO cartDetailDTO = new CartDetailDTO();
        cartDetailDTO.setVariant_id(1L);

        Cart cart = new Cart();
        when(cartRepository.findById(cartId)).thenReturn(java.util.Optional.of(cart));

        Variant variant = new Variant();
        when(variantRepository.findById(cartDetailDTO.getVariant_id())).thenReturn(java.util.Optional.of(variant));

        // Act
        ResponseEntity<?> response = cartService.addCartDetailToCart(cartId, cartDetailDTO);

        // Assert
        assertEquals(ResponseEntity.ok("Item added to cart successfully"), response);
        assertEquals(1, cart.getItems().size()); // Verify item is added to cart
        verify(cartRepository, times(1)).save(cart); // Verify cart is saved
    }

    @Test
    void getCartById_ValidInput_ReturnCart() {
        // Arrange
        Long cartId = 1L;
        Cart cart = new Cart();
        when(cartRepository.findById(cartId)).thenReturn(java.util.Optional.of(cart));

        // Act
        Cart result = cartService.getCartById(cartId);

        // Assert
        assertNotNull(result);
        assertEquals(cart, result);
    }

    @Test
    void removeCartDetailFromCart_ValidInput_ReturnOkResponse() {
        // Arrange
        Long cartId = 1L;
        Long cartDetailId = 1L;

        CartDetail cartDetail = new CartDetail();
        cartDetail.setId(cartDetailId);

        Cart cart = new Cart();
        cart.getItems().add(cartDetail);

        when(cartRepository.findById(cartId)).thenReturn(java.util.Optional.of(cart));

        // Act
        ResponseEntity<?> response = cartService.removeCartDetailFromCart(cartId, cartDetailId);

        // Assert
        assertEquals(ResponseEntity.ok("Item removed from cart successfully"), response);
        assertTrue(cart.getItems().isEmpty()); // Verify item is removed from cart
        verify(cartDetailRepository, times(1)).delete(cartDetail); // Verify cart detail is deleted
        verify(cartRepository, times(1)).save(cart); // Verify cart is saved
    }
}

