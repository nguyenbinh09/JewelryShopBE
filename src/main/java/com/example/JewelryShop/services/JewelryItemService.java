package com.example.JewelryShop.services;

import com.example.JewelryShop.dtos.JewelryItemDTO;
import com.example.JewelryShop.exceptions.NotFoundException;
import com.example.JewelryShop.models.Category;
import com.example.JewelryShop.models.JewelryItem;
import com.example.JewelryShop.repositories.CategoryRepository;
import com.example.JewelryShop.repositories.JewelryItemRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.modelmapper.ModelMapper;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@Validated
public class JewelryItemService {
    private final DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    @Autowired
    private JewelryItemRepository jewelryItemRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;

    public List<JewelryItem> getJewelryItems() {
        List<JewelryItem> jewelryItems = jewelryItemRepository.findAll();
        if (jewelryItems.isEmpty())
            throw new NotFoundException("Could not found any items");
        return jewelryItems;
    }

    public JewelryItem getJewelryItemById(Long id) {
        Optional<JewelryItem> jewelryItem = jewelryItemRepository.findById(id);
        if (jewelryItem.isEmpty())
            throw new NotFoundException("Could not find item with id " + id);
        return jewelryItem.get();
    }

    public ResponseEntity<?> addNewJewelryItem(@Valid JewelryItemDTO jewelryItemDTO) {
        Optional<Category> category = categoryRepository.findById(jewelryItemDTO.getCategory_id());
        if (category.isEmpty()) {
            throw new NotFoundException("Category with id " + jewelryItemDTO.getCategory_id() + " does not exist");
        }
        JewelryItem jewelryItem = jewelryItemDTO.toEntity();
        jewelryItem.setCategory(category.get());
        jewelryItem.setSku_code("456");
        System.out.println(jewelryItem);
        jewelryItemRepository.save(jewelryItem);
        return ResponseEntity.ok("Jewelry created successfully");
    }

    public ResponseEntity<?> updateJewelryItem(Long id, JewelryItemDTO jewelryItemDTO) {
        Optional<JewelryItem> oldJewelryItem = jewelryItemRepository.findById(id);
        if (oldJewelryItem.isEmpty()) {
            throw new NotFoundException("Jewelry with id " + id + " does not exist");
        }
        JewelryItem jewelryItem = jewelryItemDTO.toEntity(oldJewelryItem.get());
        System.out.println(jewelryItem.getId());
        jewelryItemRepository.save(jewelryItem);
        return ResponseEntity.ok("Jewelry updated successfully");
    }
}
