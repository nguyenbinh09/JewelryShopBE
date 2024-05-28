package com.example.JewelryShop.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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

    @Column(name = "username", nullable = false)
    private String username;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "avatar_id", referencedColumnName = "id")
    private Image avatar = new Image("https://i.pinimg.com/originals/c6/e5/65/c6e56503cfdd87da299f72dc416023d4.jpg");

    @Column(name = "status")
    private Boolean status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "information_id", referencedColumnName = "id")
    private PersonalInformation information;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @OneToOne(mappedBy = "user")
    private Employee employee;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @OneToOne(mappedBy = "user")
    private Customer customer;

    @Column(name = "is_employee")
    private Boolean is_employee = false;
}
