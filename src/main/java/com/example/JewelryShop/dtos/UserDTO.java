package com.example.JewelryShop.dtos;

import com.example.JewelryShop.models.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class UserDTO {
    private String uid;
    private String username;
    private PersonalInformationDTO information;

    public User toEntity() {
        User user = new User();
        user.setAccount_id(this.uid);
        user.setUsername(this.username);
        user.setInformation(this.information.toPersonalInformation());
        return user;
    }
}
