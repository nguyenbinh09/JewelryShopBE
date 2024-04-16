package com.example.JewelryShop.services;

import com.example.JewelryShop.exceptions.InternalServerErrorException;
import com.example.JewelryShop.models.User;
import com.example.JewelryShop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import java.util.List;
import java.util.Optional;

@Component
public class UserService {
    @Autowired
    private  UserRepository userRepository;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public ResponseEntity<?> addNewUser(User user) {
        User userById = userRepository.findUserById(user.getId());
        if (userById != null) {
            throw new InternalServerErrorException("User with id " + user.getId() + " already exists");
        }
        User userSaved = userRepository.save(user);
        return new ResponseEntity<String>("User created successfully", HttpStatus.CREATED);
    }

    public void deleteUser(Long userId) {
        userRepository.findById(userId);
        boolean exists =  userRepository.existsById(userId);
        if (!exists) {
            throw new IllegalStateException("User with id " + userId + " does not exist");
        }
        userRepository.deleteById(userId);
    }
}
