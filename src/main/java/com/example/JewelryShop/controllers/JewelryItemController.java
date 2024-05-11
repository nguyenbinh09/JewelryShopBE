package com.example.JewelryShop.controllers;

import com.example.JewelryShop.dtos.JewelryItemDTO;
import com.example.JewelryShop.exceptions.BadRequestException;
import com.example.JewelryShop.exceptions.InternalServerErrorException;
import com.example.JewelryShop.exceptions.NotFoundException;
import com.example.JewelryShop.models.JewelryItem;
import com.example.JewelryShop.services.JewelryItemService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin
@RestController
@RequestMapping(path = "api/v1/jewelryItem")
@Validated
public class JewelryItemController {
    @Autowired
    private JewelryItemService jewelryItemService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public List<JewelryItem> getJewelryItems() {
        try {
            return jewelryItemService.getJewelryItems();
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public JewelryItem getJewelryItemById(@PathVariable("id") Long id) {
        try {
            return jewelryItemService.getJewelryItemById(id);
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> addNewJewelryItem(@RequestBody @Valid JewelryItemDTO JewelryItemDTO) {
        try {
            return jewelryItemService.addNewJewelryItem(JewelryItemDTO);
        } catch (BadRequestException | NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateJewelryItem(@PathVariable("id") Long id, @RequestBody JewelryItemDTO JewelryItemDTO) {
        try {
            return jewelryItemService.updateJewelryItem(id, JewelryItemDTO);
        } catch (BadRequestException | NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }
}
