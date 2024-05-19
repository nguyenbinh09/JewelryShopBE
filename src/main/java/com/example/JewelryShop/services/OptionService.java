package com.example.JewelryShop.services;

import com.example.JewelryShop.dtos.OptionDTO;
import com.example.JewelryShop.dtos.OptionValueDTO;
import com.example.JewelryShop.models.Option;
import com.example.JewelryShop.models.OptionValue;
import com.example.JewelryShop.repositories.OptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class OptionService {
    @Autowired
    private OptionRepository optionRepository;

    public Option addOption(OptionDTO optionDTO) {
        Option option = optionDTO.toEntity();
        System.out.println(optionDTO);
        for (OptionValueDTO optionValueDTO : optionDTO.getValues()) {
            OptionValue optionValue = optionValueDTO.toEntity();
            optionValue.setOption(option);
            option.getOptionValues().add(optionValue);
        }
        return optionRepository.save(option);
    }
}
