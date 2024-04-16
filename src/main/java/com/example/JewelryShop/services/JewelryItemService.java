package com.example.JewelryShop.services;

import com.example.JewelryShop.exceptions.BadRequestException;
import com.example.JewelryShop.exceptions.InternalServerErrorException;
import com.example.JewelryShop.exceptions.NotFoundException;
import com.example.JewelryShop.models.JewelryItem;
import com.example.JewelryShop.repositories.JewelryItemRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@Validated
public class JewelryItemService {
    private final DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    @Autowired
    private JewelryItemRepository jewelryItemRepository;

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

    public ResponseEntity<?> addNewJewelryItem(@Valid JewelryItem jewelryItem) {
        Optional<JewelryItem> jewelryItemById = jewelryItemRepository.findById(jewelryItem.getId());
        if (jewelryItemById.isPresent()) {
            throw new BadRequestException("Jewelry with id " + jewelryItem.getId() + " already exists");
        }

        JewelryItem jewelryItemSaved = jewelryItemRepository.save(jewelryItem);
        return ResponseEntity.ok("Jewelry created successfully");
    }
}
