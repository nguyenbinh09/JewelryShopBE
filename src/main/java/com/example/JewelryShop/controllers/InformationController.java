package com.example.JewelryShop.controllers;

import com.example.JewelryShop.dtos.PersonalInformationDTO;
import com.example.JewelryShop.exceptions.InternalServerErrorException;
import com.example.JewelryShop.exceptions.NotFoundException;
import com.example.JewelryShop.models.PersonalInformation;
import com.example.JewelryShop.services.InformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(path = "api/v1/information")
public class InformationController {
    @Autowired
    private InformationService informationService;

    @GetMapping("{id}")
    public PersonalInformation getInformationById(@PathVariable Long id) {
        try {
            return informationService.getInformationById(id);
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateInformation(@PathVariable Long id, @RequestBody PersonalInformationDTO informationDTO) {
        try {
            return informationService.updateInformation(id, informationDTO);
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }
}
