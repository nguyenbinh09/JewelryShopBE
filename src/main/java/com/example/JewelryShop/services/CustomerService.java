package com.example.JewelryShop.services;

import com.example.JewelryShop.exceptions.NotFoundException;
import com.example.JewelryShop.models.*;
import com.example.JewelryShop.repositories.CustomerRepository;
import com.example.JewelryShop.repositories.JewelryItemRepository;
import com.example.JewelryShop.repositories.UserRepository;
import com.example.JewelryShop.repositories.VariantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    @Autowired
    private JewelryItemRepository jewelryItemRepository;

    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new NotFoundException("Customer with id " + id + " does not exist"));
    }

    public Customer getCustomerByUserId(Long userId) {
        return customerRepository.findByUserId(userId);
    }

    public void addNewCustomer(Long userId) {
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
    }

    public ResponseEntity<?> addWishlistDetailToWishlist(Long customerId, Long jewelryId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new NotFoundException("Customer with id " + customerId + " does not exist"));
        JewelryItem jewelryItem = jewelryItemRepository.findById(jewelryId).orElseThrow(() -> new NotFoundException("Jewelry item with id " + jewelryId + " does not exist"));
        WishlistDetail wishlistDetail = new WishlistDetail();
        wishlistDetail.setJewelryItem(jewelryItem);
        wishlistDetail.setCustomer(customer);
        customer.getWishlist().add(wishlistDetail);
        customerRepository.save(customer);
        return ResponseEntity.ok("Item added to wishlist successfully");
    }

    public ResponseEntity<?> removeItemFromWishlist(Long customerId, Long wishlistDetailId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new NotFoundException("Customer with id " + customerId + " does not exist"));
        WishlistDetail wishlistDetail = customer.getWishlist().stream().filter(item -> item.getId().equals(wishlistDetailId)).findFirst().orElseThrow(() -> new NotFoundException("Wishlist detail with id " + wishlistDetailId + " does not exist"));
        customer.getWishlist().remove(wishlistDetail);
        customerRepository.save(customer);
        return ResponseEntity.ok("Item removed from wishlist successfully");
    }
}
