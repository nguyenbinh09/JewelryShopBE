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
    public List<JewelryItemDTO> getJewelryItems() {
        try {
            List<JewelryItem> jewelryItems = jewelryItemService.getJewelryItems();
            System.out.println(jewelryItems);
            List<JewelryItemDTO> jewelryItemDTOS = jewelryItems.stream().map(jewelryItem -> modelMapper.map(jewelryItem, JewelryItemDTO.class)).toList();
            return jewelryItemDTOS;
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException();
        }
    }

    @GetMapping("/{id}")
    public JewelryItemDTO getJewelryItemById(@PathVariable("id") Long id) {
        try {
            JewelryItem jewelryItem = jewelryItemService.getJewelryItemById(id);
            JewelryItemDTO jewelryItemResponse = modelMapper.map(jewelryItem, JewelryItemDTO.class);
            return jewelryItemResponse;
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException();
        }
    }

    @PostMapping
    public ResponseEntity<?> addNewJewelryItem(@RequestBody @Valid JewelryItemDTO jewelryItemDTO) {
        try {
            JewelryItem jewelryItem = modelMapper.map(jewelryItemDTO, JewelryItem.class);
            return jewelryItemService.addNewJewelryItem(jewelryItem);
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException();
        }
    }
}
