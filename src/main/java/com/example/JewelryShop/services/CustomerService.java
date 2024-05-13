package com.example.JewelryShop.services;

import com.example.JewelryShop.exceptions.NotFoundException;
import com.example.JewelryShop.models.*;
import com.example.JewelryShop.repositories.CustomerRepository;
import com.example.JewelryShop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

@Service
@Validated
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private UserRepository userRepository;

    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new NotFoundException("Customer with id " + id + " does not exist"));
    }

    public Customer addNewCustomer(Long userId) {
        Customer customer = new Customer();
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User with id " + userId + " does not exist"));
        customer.setUser(user);
        Cart cart = new Cart();
        cart.setCustomer(customer);
        customer.setCart(cart);
        List<WishlistDetail> wishlistDetail = new ArrayList<>();
        customer.setWishlist(wishlistDetail);
        List<Order> orders = new ArrayList<>();
        customer.setOrder_history(orders);
        return customerRepository.save(customer);
    }
}
