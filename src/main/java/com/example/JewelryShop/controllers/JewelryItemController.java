package com.example.JewelryShop.controllers;

import com.example.JewelryShop.dtos.JewelryItemDTO;
import com.example.JewelryShop.dtos.OptionDTO;
import com.example.JewelryShop.exceptions.BadRequestException;
import com.example.JewelryShop.exceptions.InternalServerErrorException;
import com.example.JewelryShop.exceptions.NotFoundException;
import com.example.JewelryShop.models.JewelryItem;
import com.example.JewelryShop.services.JewelryItemService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    @Autowired
    private ObjectMapper objectMapper;

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

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public JewelryItem addNewJewelryItem(@RequestPart("jewelryItem") String jewelryItemJson,
                                         @RequestPart("images") List<MultipartFile> images
    ) throws JsonProcessingException {
        JewelryItemDTO jewelryItemDTO = objectMapper.readValue(jewelryItemJson, JewelryItemDTO.class);
        try {
            return jewelryItemService.addNewJewelryItem(jewelryItemDTO, images);
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

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteJewelryItem(@PathVariable("id") Long id) {
        try {
            return jewelryItemService.deleteJewelryItem(id);
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }
}
