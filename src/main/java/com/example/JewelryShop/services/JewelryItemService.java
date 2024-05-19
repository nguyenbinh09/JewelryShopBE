package com.example.JewelryShop.services;

import com.example.JewelryShop.dtos.JewelryItemDTO;
import com.example.JewelryShop.dtos.OptionDTO;
import com.example.JewelryShop.dtos.OptionValueDTO;
import com.example.JewelryShop.exceptions.NotFoundException;
import com.example.JewelryShop.models.*;
import com.example.JewelryShop.repositories.CategoryRepository;
import com.example.JewelryShop.repositories.JewelryItemRepository;
import jakarta.transaction.Transactional;
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
    private OptionService optionService;
    @Autowired
    private ModelMapper modelMapper;

    private String generateSku(JewelryItem jewelryItem) {
        String prefix = jewelryItem.getCategory().getCode();
        String idPart = String.format("%04d", jewelryItem.getId()); // Zero-padded ID
        return prefix + "-JEW" + idPart;
    }

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

    @Transactional
    public ResponseEntity<?> addNewJewelryItem(@Valid JewelryItemDTO jewelryItemDTO) {
        Optional<Category> category = categoryRepository.findById(jewelryItemDTO.getCategory_id());
        if (category.isEmpty()) {
            throw new NotFoundException("Category with id " + jewelryItemDTO.getCategory_id() + " does not exist");
        }
        JewelryItem jewelryItem = jewelryItemDTO.toEntity();
        jewelryItem.setCategory(category.get());
        for (OptionDTO optionDTO : jewelryItemDTO.getOptions()) {
            Option option = optionService.addOption(optionDTO);
            option.setJewelry_item(jewelryItem);
            jewelryItem.getOptions().add(option);
        }
        JewelryItem jewelryItemSaved = jewelryItemRepository.save(jewelryItem);
        jewelryItem.setSku_code(generateSku(jewelryItemSaved));
        jewelryItemRepository.save(jewelryItemSaved);
        return ResponseEntity.ok("Jewelry created successfully");
    }

    @Transactional
    public ResponseEntity<?> updateJewelryItem(Long id, JewelryItemDTO jewelryItemDTO) {
        Optional<JewelryItem> oldJewelryItem = jewelryItemRepository.findById(id);
        if (oldJewelryItem.isEmpty()) {
            throw new NotFoundException("Jewelry with id " + id + " does not exist");
        }
        JewelryItem jewelryItem = jewelryItemDTO.toEntity(oldJewelryItem.get());
        jewelryItemRepository.save(jewelryItem);
        return ResponseEntity.ok("Jewelry updated successfully");
    }

    public ResponseEntity<?> deleteJewelryItem(Long id) {
        Optional<JewelryItem> jewelryItem = jewelryItemRepository.findById(id);
        if (jewelryItem.isEmpty()) {
            throw new NotFoundException("Jewelry with id " + id + " does not exist");
        }
        jewelryItem.get().setIs_deleted(true);
        jewelryItemRepository.save(jewelryItem.get());
        return ResponseEntity.ok("Jewelry deleted successfully");
    }
}
