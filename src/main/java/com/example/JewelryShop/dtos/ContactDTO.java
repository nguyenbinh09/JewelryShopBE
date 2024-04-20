package com.example.JewelryShop.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ContactDTO extends BaseDTO {
    private String email;
    private String phone;
    private String address;
}
