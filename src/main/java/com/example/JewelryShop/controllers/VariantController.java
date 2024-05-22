package com.example.JewelryShop.controllers;

import com.example.JewelryShop.dtos.VariantUpdateDTO;
import com.example.JewelryShop.exceptions.BadRequestException;
import com.example.JewelryShop.exceptions.InternalServerErrorException;
import com.example.JewelryShop.exceptions.NotFoundException;
import com.example.JewelryShop.models.Variant;
import com.example.JewelryShop.services.VariantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(path = "api/v1/variant")
@Validated
public class VariantController {
    @Autowired
    private VariantService variantService;

    @PostMapping("/create_variants_for_item/{jewelryItemId}")
    public ResponseEntity<?> createVariantsForItem(@PathVariable Long jewelryItemId) {
        try {
            return variantService.createVariantsForItem(jewelryItemId);
        } catch (BadRequestException | NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @GetMapping("/jewelryItem/{jewelryItemId}")
    public List<Variant> getVariantByOrderId(@PathVariable Long jewelryItemId) {
        try {
            return variantService.getVariantByJewelryItemId(jewelryItemId);
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @PutMapping("/{variantId}")
    public ResponseEntity<?> updateVariant(@PathVariable Long variantId, @RequestBody VariantUpdateDTO variantUpdateDTO) {
        try {
            return variantService.updateVariant(variantId, variantUpdateDTO);
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @DeleteMapping("/{variantId}")
    public ResponseEntity<?> deleteVariant(@PathVariable Long variantId) {
        try {
            return variantService.deleteVariant(variantId);
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @PutMapping(value = "/upload_image/{variantId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImage(@PathVariable Long variantId, @RequestPart("images") List<MultipartFile> images) {
        try {
            return variantService.uploadVariantImage(variantId, images);
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @PutMapping(value = "/delete_image_from_variant/{variantId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> deleteImageFromVariant(@PathVariable Long variantId, @RequestBody List<Long> images) {
        try {
            return variantService.deleteVariantImage(variantId, images);
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

}
