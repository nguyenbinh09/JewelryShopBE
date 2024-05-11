package com.example.JewelryShop.dtos;

import com.example.JewelryShop.models.Contact;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class ContactDTO {
    private String email;
    private String phone;
    private String address;

    public Contact toEntity() {
        Contact contact = new Contact();
        contact.setEmail(this.email);
        contact.setPhone(this.phone);
        contact.setAddress(this.address);
        return contact;
    }
}
