package com.example.JewelryShop.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
public class PersonalInformationDTO {
    private String name;
    private Date birthday;
    private String gender;
    private Long contact_id;
}
