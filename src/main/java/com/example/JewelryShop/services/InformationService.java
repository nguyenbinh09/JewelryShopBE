package com.example.JewelryShop.services;

import com.example.JewelryShop.dtos.PersonalInformationDTO;
import com.example.JewelryShop.exceptions.NotFoundException;
import com.example.JewelryShop.models.PersonalInformation;
import com.example.JewelryShop.repositories.InformationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class InformationService {
    @Autowired
    private InformationRepository informationRepository;

    public PersonalInformation getInformationById(Long informationId) {
        return informationRepository.findById(informationId).orElseThrow(() -> new NotFoundException("Information with id " + informationId + " not found"));
    }

    public ResponseEntity<?> updateInformation(Long informationId, PersonalInformationDTO informationDTO) {
        PersonalInformation informationToUpdate = informationRepository.findById(informationId).orElseThrow(() -> new NotFoundException("Information with id " + informationId + " not found"));

        return ResponseEntity.ok("Information updated successfully");
    }
}
