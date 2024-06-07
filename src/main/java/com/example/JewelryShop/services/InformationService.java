package com.example.JewelryShop.services;

import com.example.JewelryShop.dtos.PersonalInformationDTO;
import com.example.JewelryShop.exceptions.NotFoundException;
import com.example.JewelryShop.models.Contact;
import com.example.JewelryShop.models.PersonalInformation;
import com.example.JewelryShop.repositories.ContactRepository;
import com.example.JewelryShop.repositories.InformationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class InformationService {
    @Autowired
    private InformationRepository informationRepository;
    @Autowired
    private ContactRepository contactRepository;

    public PersonalInformation getInformationById(Long informationId) {
        return informationRepository.findById(informationId).orElseThrow(() -> new NotFoundException("Information with id " + informationId + " not found"));
    }

    public ResponseEntity<?> updateInformation(Long informationId, PersonalInformationDTO informationDTO) {
        PersonalInformation informationToUpdate = informationRepository.findById(informationId).orElseThrow(() -> new NotFoundException("Information with id " + informationId + " not found"));
        informationDTO.toEntity(informationToUpdate);
        if (informationDTO.getContact_id() != null) {
            Contact contact = contactRepository.findById(informationToUpdate.getContact().getId()).orElseThrow(() -> new NotFoundException("Contact with id " + informationDTO.getContact_id() + " not found"));
            informationDTO.getContact_id().toEntity(contact);
            informationToUpdate.setContact(contact);
        }
        informationRepository.save(informationToUpdate);
        return ResponseEntity.ok("Information updated successfully");
    }
}
