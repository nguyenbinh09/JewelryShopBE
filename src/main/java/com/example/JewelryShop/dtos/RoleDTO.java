package com.example.JewelryShop.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RoleDTO extends BaseDTO {
    private String name;
    private String description;
}
