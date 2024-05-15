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
    @Column(name = "account_id", unique = true, nullable = false)
    private String account_id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "avatar_id", referencedColumnName = "id")
    private Image avatar = new Image("https://i.pinimg.com/originals/c6/e5/65/c6e56503cfdd87da299f72dc416023d4.jpg");

    @Column(name = "status")
    private Boolean status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "information_id", referencedColumnName = "id")
    private PersonalInformation information;

    @OneToOne(mappedBy = "user")
    private Employee employee;

    @OneToOne(mappedBy = "user")
    private Customer customer;

    @Column(name = "is_employee")
    private Boolean is_employee = false;
}
