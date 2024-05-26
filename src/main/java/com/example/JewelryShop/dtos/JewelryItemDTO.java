package com.example.JewelryShop.dtos;

import com.example.JewelryShop.models.JewelryItem;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class JewelryItemDTO {
    @NotNull(message = "Name is mandatory")
    private String name;
    private String description;
    @NotNull(message = "Category id is mandatory")
    private Long category_id;
    @NotNull(message = "Price is mandatory")
    private Double price;
    List<OptionDTO> options;

    public JewelryItem toEntity() {
        JewelryItem jewelryItem = new JewelryItem();
        jewelryItem.setName(this.name);
        jewelryItem.setDescription(this.description);
        jewelryItem.setPrice(this.price);
        return jewelryItem;
    }

    public JewelryItem toEntity(JewelryItem jewelryItem) {
        if (this.name != null)
            jewelryItem.setName(this.name);
        if (this.description != null)
            jewelryItem.setDescription(this.description);
        if (this.price != null)
            jewelryItem.setPrice(this.price);
        return jewelryItem;
    }
}
