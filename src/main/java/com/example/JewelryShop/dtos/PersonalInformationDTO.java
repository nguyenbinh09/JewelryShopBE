package com.example.JewelryShop.dtos;

import com.example.JewelryShop.models.PersonalInformation;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
public class PersonalInformationDTO {
    private String name;
    private Date birthday;
    private String gender;
    private ContactDTO contact_id;

    public PersonalInformation toPersonalInformation() {
        PersonalInformation personalInformation = new PersonalInformation();
        personalInformation.setName(this.name);
        personalInformation.setBirthday(this.birthday);
        personalInformation.setGender(this.gender);
        personalInformation.setContact(this.contact_id.toEntity());
        return personalInformation;
    }
}
