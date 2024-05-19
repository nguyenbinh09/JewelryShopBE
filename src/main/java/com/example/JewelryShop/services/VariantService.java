package com.example.JewelryShop.services;

import com.example.JewelryShop.dtos.ImageDTO;
import com.example.JewelryShop.dtos.VariantUpdateDTO;
import com.example.JewelryShop.exceptions.NotFoundException;
import com.example.JewelryShop.models.*;
import com.example.JewelryShop.repositories.JewelryItemRepository;
import com.example.JewelryShop.repositories.VariantOptionValueRepository;
import com.example.JewelryShop.repositories.VariantRepository;
import com.example.JewelryShop.utils.CartesianVariant;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Validated
public class VariantService {
    @Autowired
    VariantRepository variantRepository;
    @Autowired
    JewelryItemRepository jewelryItemRepository;
    @Autowired
    VariantOptionValueRepository variantOptionValueRepository;

    @Transactional
    public ResponseEntity<?> createVariantsForItem(Long jewelryItemId) {
        JewelryItem jewelryItem = jewelryItemRepository.findById(jewelryItemId).orElseThrow(() -> new NotFoundException("Jewelry item with id " + jewelryItemId + " does not exist"));
        List<Option> options = jewelryItem.getOptions();
        if (options.isEmpty()) {
            throw new NotFoundException("Jewelry item with id " + jewelryItemId + " does not have any options");
        }
        List<List<OptionValue>> valueOnlyArr = options.stream()
                .map(Option::getOptionValues)
                .toList();
        List<List<OptionValue>> cartesianArr = new CartesianVariant().cartesian(valueOnlyArr, 0);
        System.out.println(cartesianArr.size());
        for (List<OptionValue> optionValues : cartesianArr) {
            Variant variant = new Variant();
            variant.setJewelry_item(jewelryItem);
            variant.setName(jewelryItem.getName());
            variant.setQuantity(0);
            variant.setPrice(0.0);
            Variant savedVariant = variantRepository.save(variant);

            List<VariantOptionValue> variantOptionValues = optionValues.stream().map(optionValue -> {
                VariantOptionValue variantOptionValue = new VariantOptionValue();
                variantOptionValue.setVariant(savedVariant);
                variantOptionValue.setOption_value(optionValue);
                return variantOptionValue;
            }).collect(Collectors.toList());

            variantOptionValueRepository.saveAll(variantOptionValues);
            variant.setVariantOptionValues(variantOptionValues);
            variantRepository.save(savedVariant);
        }
        ;
//        return ResponseEntity.ok("Variant added successfully");
        return ResponseEntity.ok("Variants added successfully");
    }

    public List<Variant> getVariantByJewelryItemId(Long jewelryItemId) {
        return variantRepository.findAllByJewelryItemId(jewelryItemId);
    }

    public ResponseEntity<?> updateVariant(Long variantId, VariantUpdateDTO variantUpdateDTO) {
        Variant variant = variantRepository.findById(variantId).orElseThrow(() -> new NotFoundException("Variant with id " + variantId + " does not exist"));
        variantUpdateDTO.toEntity(variant);
        if (variantUpdateDTO.getImages() != null) {
            List<Long> updatedImages = variantUpdateDTO.getImages().stream()
                    .map(ImageDTO::getId).filter(id -> id != null)
                    .collect(Collectors.toList());
            variant.getImages().removeIf(image -> image.getId() != null && !updatedImages.contains(image.getId()));
            System.out.println(variantUpdateDTO.getImages());
            for (ImageDTO image : variantUpdateDTO.getImages()) {
                Optional<Image> existingImage = variant.getImages().stream()
                        .filter(img -> image.getId() != null && img.getId().equals(image.getId()))
                        .findFirst();
                if (existingImage.isPresent()) {
                    existingImage.get().setUrl(image.getUrl());
                } else {
                    Image img = new Image();
                    img.setUrl(image.getUrl());
                    img.setVariant(variant);
                    variant.getImages().add(img);
                }
            }
        }
        variantRepository.save(variant);
        return ResponseEntity.ok("Variant updated successfully");
    }
}
