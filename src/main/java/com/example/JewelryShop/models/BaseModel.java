package com.example.JewelryShop.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Date;
@MappedSuperclass
@Data
public abstract class BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date created_at;
    private Date updated_at;
    private String created_by;
    private String updated_by;
    private Boolean is_deleted;
}
