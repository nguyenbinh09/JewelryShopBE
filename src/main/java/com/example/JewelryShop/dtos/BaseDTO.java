package com.example.JewelryShop.dtos;

import lombok.Data;

import java.util.Date;

@Data
public abstract class BaseDTO {
    private Long id;
    private Date created_at;
    private Date updated_at;
    private Long created_by;
    private Long updated_by;
    private Boolean is_deleted;
}
