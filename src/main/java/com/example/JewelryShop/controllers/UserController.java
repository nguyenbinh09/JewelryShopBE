package com.example.JewelryShop.controllers;

import com.example.JewelryShop.models.User;
import com.example.JewelryShop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }
    @PostMapping
    public ResponseEntity<?> addNewUser(@RequestBody User user) {
        return userService.addNewUser(user);
    }
    @DeleteMapping(path = "{userId}")
    public void deleteUser(@PathVariable("userId") Long userId) {
        userService.deleteUser(userId);
    }
}
