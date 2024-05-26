package com.example.JewelryShop.dtos;

import com.example.JewelryShop.models.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class RoleDTO {
    private String name;
    private String description;
    private String code;

    public Role toEntity() {
        Role role = new Role();
        role.setName(name);
        role.setDescription(description);
        role.setCode(code);
        return role;
    }

    public Role toEntity(Role role) {
        if (this.name != null) role.setName(this.name);
        if (this.description != null) role.setDescription(this.description);
        if (this.code != null) role.setCode(this.code);
        return role;
    }
}
