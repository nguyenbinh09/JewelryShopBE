package com.example.JewelryShop.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table
public class Contact extends BaseModel {
    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @JsonIgnore
    @OneToOne(mappedBy = "contact")
    private PersonalInformation information;

    @JsonIgnore
    @OneToOne(mappedBy = "contact")
    private Order order;
}