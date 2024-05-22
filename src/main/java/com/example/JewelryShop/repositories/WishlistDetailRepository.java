package com.example.JewelryShop.repositories;

import com.example.JewelryShop.models.WishlistDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishlistDetailRepository extends JpaRepository<WishlistDetail, Long> {
    List<WishlistDetail> findAllByCustomerId(Long customerId);
}
