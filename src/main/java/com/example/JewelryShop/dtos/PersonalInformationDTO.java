package com.example.JewelryShop.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class PersonalInformationDTO extends BaseDTO {
    private String name;
    private Date birthday;
    private String gender;
    private Long contact_id;
}
