package com.example.JewelryShop.repositories;

import com.example.JewelryShop.models.Option;
import com.example.JewelryShop.models.VariantOptionValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VariantOptionValueRepository extends JpaRepository<VariantOptionValue, Long> {
    @Query("SELECT vov FROM VariantOptionValue vov WHERE vov.variant.id = ?1")
    Optional<List<VariantOptionValue>> findByVariantId(Long id);
}
