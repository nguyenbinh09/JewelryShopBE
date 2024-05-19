package com.example.JewelryShop.repositories;

import com.example.JewelryShop.models.VariantOptionValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VariantOptionValueRepository extends JpaRepository<VariantOptionValue, Long> {
}
