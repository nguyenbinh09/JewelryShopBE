package com.example.JewelryShop.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table
public class Category extends BaseModel {
    @Column(name = "type")
    @NotNull(message = "Type's name is mandatory")
    private String type;
}
