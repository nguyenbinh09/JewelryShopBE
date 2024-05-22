package com.example.JewelryShop.services;

import com.example.JewelryShop.exceptions.NotFoundException;
import com.example.JewelryShop.models.Customer;
import com.example.JewelryShop.models.WishlistDetail;
import com.example.JewelryShop.repositories.CustomerRepository;
import com.example.JewelryShop.repositories.WishlistDetailRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
@Validated
public class WishlistDetailService {
    @Autowired
    private WishlistDetailRepository wishlistDetailRepository;
    @Autowired
    private CustomerRepository customerRepository;

    public List<WishlistDetail> getWishlistDetailsByCustomerId(@Valid Long customerId) {
        customerRepository.findById(customerId).orElseThrow(() -> new NotFoundException("Customer with id " + customerId + " does not exist"));
        return wishlistDetailRepository.findAllByCustomerId(customerId);
    }
}
