package com.example.JewelryShop.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Table
@EqualsAndHashCode(callSuper = true)
public class User extends  BaseModel{
    private String name;
    private String email;
    private String phoneNumber;
}
