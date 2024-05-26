package com.example.JewelryShop.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table
public class PaymentMethod extends BaseModel {
    @Column(name = "type")
    @NotNull(message = "Type's name is mandatory")
    private String type;
}
