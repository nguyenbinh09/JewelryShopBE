package com.example.JewelryShop.services;

import com.example.JewelryShop.dtos.UserDTO;
import com.example.JewelryShop.exceptions.BadRequestException;
import com.example.JewelryShop.exceptions.InternalServerErrorException;
import com.example.JewelryShop.exceptions.NotFoundException;
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

    public User getUserByAccountId(String accountId) {
        User user = userRepository.findUserByAccountId(accountId);
        if (user == null) {
            throw new BadRequestException("User with account id " + accountId + " does not exist");
        }
        return user;
    }

    @Transactional
    public ResponseEntity<?> addNewUser(@Valid UserDTO userDTO) {
        User user = userDTO.toEntity();
        userRepository.save(user);
        customerService.addNewCustomer(user.getId());
        return ResponseEntity.ok("User created successfully");
    }

    public ResponseEntity<?> deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new BadRequestException("User with id " + userId + " does not exist"));
        user.setIs_deleted(true);
        user.getInformation().setIs_deleted(true);
        user.getInformation().getContact().setIs_deleted(true);
        userRepository.save(user);
        return ResponseEntity.ok("User deleted successfully");
    }

    public ResponseEntity<?> addEmployee(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new BadRequestException("User with id " + userId + " does not exist"));
        user.setIs_employee(true);
        userRepository.save(user);
        return ResponseEntity.ok("Employee added successfully");
    }
}
