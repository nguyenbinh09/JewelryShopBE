package com.example.JewelryShop.repositories;

import com.example.JewelryShop.models.Variant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VariantRepository extends JpaRepository<Variant, Long> {
    @Query("SELECT v FROM Variant v WHERE v.jewelry_item.id = ?1")
    List<Variant> findAllByJewelryItemId(Long jewelryItemId);
}
