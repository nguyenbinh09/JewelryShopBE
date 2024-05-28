package com.example.JewelryShop.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class PaymentMethodDTO {
    @NotNull(message = "Type is required")
    private String type;
}
