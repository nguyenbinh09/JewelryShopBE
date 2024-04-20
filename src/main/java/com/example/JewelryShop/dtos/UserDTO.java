package com.example.JewelryShop.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserDTO extends BaseDTO {
    private Long account_id;
    private String avatar;
    private Boolean status;
    private Long information_id;
    private Boolean is_employee;
}
