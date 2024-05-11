package com.example.JewelryShop.dtos;

import com.example.JewelryShop.models.JewelryItem;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
public class JewelryItemDTO {
    @NotNull(message = "Name is mandatory")
    private String name;
    private String description;
    @NotNull(message = "Category id is mandatory")
    private Long category_id;
    @NotNull(message = "Material id is mandatory")
    private String material;
    private String color;
    @NotNull(message = "Color is mandatory")
    private String metal_color;
    private String stone_color;
    private String image;
    @NotNull(message = "Price is mandatory")
    private Double price;
    private Integer quantity;

    public JewelryItem toEntity() {
        JewelryItem jewelryItem = new JewelryItem();
        jewelryItem.setName(this.name);
        jewelryItem.setDescription(this.description);
        jewelryItem.setMaterial(this.material);
        jewelryItem.setColor(this.color);
        jewelryItem.setMetal_color(this.metal_color);
        jewelryItem.setStone_color(this.stone_color);
        jewelryItem.setImage(this.image);
        jewelryItem.setPrice(this.price);
        jewelryItem.setQuantity(this.quantity);
        return jewelryItem;
    }

    public JewelryItem toEntity(JewelryItem jewelryItem) {
        if (this.name != null)
            jewelryItem.setName(this.name);
        if (this.description != null)
            jewelryItem.setDescription(this.description);
        if (this.material != null)
            jewelryItem.setMaterial(this.material);
        if (this.color != null)
            jewelryItem.setColor(this.color);
        if (this.metal_color != null)
            jewelryItem.setMetal_color(this.metal_color);
        if (this.stone_color != null)
            jewelryItem.setStone_color(this.stone_color);
        if (this.image != null)
            jewelryItem.setImage(this.image);
        if (this.price != null)
            jewelryItem.setPrice(this.price);
        if (this.quantity != null)
            jewelryItem.setQuantity(this.quantity);
        return jewelryItem;
    }
}
