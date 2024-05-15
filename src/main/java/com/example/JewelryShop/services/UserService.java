package com.example.JewelryShop.services;

import com.example.JewelryShop.dtos.UserDTO;
import com.example.JewelryShop.exceptions.InternalServerErrorException;
import com.example.JewelryShop.models.User;
import com.example.JewelryShop.repositories.CustomerRepository;
import com.example.JewelryShop.repositories.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import java.util.List;
import java.util.Optional;

@Service
@Validated
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomerService customerService;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public ResponseEntity<?> addNewUser(@Valid UserDTO userDTO) {
        User user = userDTO.toEntity();
        userRepository.save(user);
        customerService.addNewCustomer(user.getId());
        return ResponseEntity.ok("User created successfully");
    }

    public void deleteUser(Long userId) {
        userRepository.findById(userId);
        boolean exists = userRepository.existsById(userId);
        if (!exists) {
            throw new IllegalStateException("User with id " + userId + " does not exist");
        }
        userRepository.deleteById(userId);
    }
}
