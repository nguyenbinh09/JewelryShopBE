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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
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
    @Autowired
    CloudinaryService cloudinaryService;

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
            variant.setPrice(0);
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

    @Transactional
    public ResponseEntity<?> updateVariant(Long variantId, VariantUpdateDTO variantUpdateDTO) {
        Variant variant = variantRepository.findById(variantId).orElseThrow(() -> new NotFoundException("Variant with id " + variantId + " does not exist"));
        JewelryItem jewelryItem = variant.getJewelry_item();
        if (variantUpdateDTO.getPrice() != null)
            variant.setPrice(variantUpdateDTO.getPrice());
        if (variantUpdateDTO.getQuantity() != null) {
            jewelryItem.setQuantity(jewelryItem.getQuantity() + variantUpdateDTO.getQuantity() - variant.getQuantity());
            System.out.println(jewelryItem.getQuantity());
            variant.setQuantity(variantUpdateDTO.getQuantity());
        }
        variantRepository.save(variant);
        return ResponseEntity.ok("Variant updated successfully");
    }

    @Transactional
    public ResponseEntity<?> uploadVariantImage(Long variantId, List<MultipartFile> images) throws IOException {
        Variant variant = variantRepository.findById(variantId).orElseThrow(() -> new NotFoundException("Variant with id " + variantId + " does not exist"));
        for (MultipartFile image : images) {
            Image imageEntity = new Image();
            String imageUrl = cloudinaryService.upload(image);
            imageEntity.setUrl(imageUrl);
            imageEntity.setVariant(variant);
            variant.getImages().add(imageEntity);
        }
        variantRepository.save(variant);
        return ResponseEntity.ok("Variant image updated successfully");
    }

    public ResponseEntity<?> deleteVariant(Long variantId) {
        Variant variant = variantRepository.findById(variantId).orElseThrow(() -> new NotFoundException("Variant with id " + variantId + " does not exist"));
        variant.setIs_deleted(true);
        variantRepository.save(variant);
        return ResponseEntity.ok("Variant deleted successfully");
    }

    @Transactional
    public ResponseEntity<?> deleteVariantImage(Long variantId, List<Long> images) {
        Variant variant = variantRepository.findById(variantId)
                .orElseThrow(() -> new NotFoundException("Variant with id " + variantId + " does not exist"));

        List<Image> variantImages = new ArrayList<>(variant.getImages()); // Create a mutable list

        for (Long id : images) {
            Optional<Image> image = variantImages.stream()
                    .filter(img -> img.getId().equals(id))
                    .findFirst();
            image.ifPresent(value -> {
                value.setIs_deleted(true);
                variantImages.remove(value);
            });
        }

        variant.setImages(variantImages); // Update the variant's images list with the modified one
        variantRepository.save(variant);

        return ResponseEntity.ok("Images deleted successfully");
    }

}
