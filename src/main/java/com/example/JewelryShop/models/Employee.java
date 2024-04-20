package com.example.JewelryShop.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table
public class Employee extends BaseModel {
    @Column(name = "code", unique = true)
    private String code;

    @Column(name = "user_id", unique = true)
    private Long user_id;

    @Column(name = "role_id")
    private Long role_id;
}
