package com.example.JewelryShop.repositories;

import com.example.JewelryShop.models.Cart;
import com.example.JewelryShop.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
}
