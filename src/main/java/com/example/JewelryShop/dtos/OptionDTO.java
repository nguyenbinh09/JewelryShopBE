package com.example.JewelryShop.dtos;

import com.example.JewelryShop.models.Option;
import lombok.Data;

import java.util.List;

@Data
public class OptionDTO {
    private String name;
    private List<OptionValueDTO> values;

    public Option toEntity() {
        Option option = new Option();
        option.setName(this.name);
        return option;
    }
}
