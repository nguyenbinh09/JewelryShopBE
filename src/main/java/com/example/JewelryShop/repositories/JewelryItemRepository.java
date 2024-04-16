package com.example.JewelryShop.repositories;

import com.example.JewelryShop.models.JewelryItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JewelryItemRepository extends JpaRepository<JewelryItem, Long> {
    JewelryItem findJewelryItemById(Long id);
}
