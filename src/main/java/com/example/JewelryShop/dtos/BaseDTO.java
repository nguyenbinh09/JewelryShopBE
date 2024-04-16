package com.example.JewelryShop.dtos;

import lombok.Data;

import java.util.Date;

@Data
public abstract class BaseDTO {
    private Long id;
    private Date created_at;
    private Date updated_at;
    private String created_by;
    private String updated_by;
    private Boolean is_deleted;
}
