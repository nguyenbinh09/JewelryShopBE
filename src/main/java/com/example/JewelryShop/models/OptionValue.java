package com.example.JewelryShop.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table
public class OptionValue extends BaseModel {
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id")
    private Option option;

    @Column(name = "value")
    private String value; // e.g., Medium, Red

    @JsonIgnore
    @OneToMany(mappedBy = "option_value", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VariantOptionValue> variantOptionValues = new ArrayList<>();
}
