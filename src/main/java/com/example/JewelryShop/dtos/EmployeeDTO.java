package com.example.JewelryShop.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class EmployeeDTO {
    private String code;
    private Long user_id;
    private Long role_id;
}
