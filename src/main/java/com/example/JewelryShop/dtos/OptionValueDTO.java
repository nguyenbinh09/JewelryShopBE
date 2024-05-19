package com.example.JewelryShop.dtos;

import com.example.JewelryShop.models.OptionValue;
import lombok.Data;

@Data
public class OptionValueDTO {
    private String value;

    public OptionValue toEntity() {
        OptionValue optionValue = new OptionValue();
        optionValue.setValue(this.value);
        return optionValue;
    }
}
