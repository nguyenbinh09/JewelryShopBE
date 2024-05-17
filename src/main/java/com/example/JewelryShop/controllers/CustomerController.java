package com.example.JewelryShop.controllers;

import com.example.JewelryShop.exceptions.InternalServerErrorException;
import com.example.JewelryShop.exceptions.NotFoundException;
import com.example.JewelryShop.models.Customer;
import com.example.JewelryShop.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "api/v1/customer")
@Validated
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping
    public List<Customer> getCustomers() {
        try {
            return customerService.getCustomers();
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @GetMapping("/byUserId/{user_id}")
    public Customer getCustomerByUserId(Long user_id) {
        try {
            return customerService.getCustomerByUserId(user_id);
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Customer getCustomerById(Long id) {
        try {
            return customerService.getCustomerById(id);
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }
}
