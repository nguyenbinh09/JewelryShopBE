package com.example.JewelryShop.repositories;

import com.example.JewelryShop.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("SELECT r FROM Review r WHERE r.jewelry_item.id = ?1")
    List<Review> findByJewelryItemId(Long jewelryItemId);
}
