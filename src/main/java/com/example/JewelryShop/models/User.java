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
public class User extends BaseModel {
    @Column(name = "account_id", unique = true)
    private Long account_id;

    @Column(name = "avatar")
    private String avatar = "https://i.pinimg.com/originals/c6/e5/65/c6e56503cfdd87da299f72dc416023d4.jpg";

    @Column(name = "status")
    private Boolean status;

    @Column(name = "information_id", unique = true)
    private Long information_id;

    @Column(name = "is_employee")
    private Boolean is_employee;
}
