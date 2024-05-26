package com.example.JewelryShop.services;

import com.example.JewelryShop.exceptions.NotFoundException;
import com.example.JewelryShop.models.VariantOptionValue;
import com.example.JewelryShop.repositories.VariantOptionValueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VariantOptionValueService {
    @Autowired
    private VariantOptionValueRepository variantOptionValueRepository;

    public List<VariantOptionValue> getVariantOptionValueVariantId(Long id) {
        return variantOptionValueRepository.findByVariantId(id).orElseThrow(() -> new NotFoundException("Could not find variant option value with variant id " + id));
    }
}
