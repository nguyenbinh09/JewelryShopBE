package com.example.JewelryShop.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class UserDTO {
    private Long account_id;
    private String avatar;
    private Boolean status;
    private Long information_id;
    private Boolean is_employee;
}
