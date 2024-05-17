package com.example.JewelryShop.controllers;

import com.example.JewelryShop.dtos.UserDTO;
import com.example.JewelryShop.exceptions.InternalServerErrorException;
import com.example.JewelryShop.exceptions.NotFoundException;
import com.example.JewelryShop.models.User;
import com.example.JewelryShop.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(path = "api/v1/user")
@Validated
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{account_id}")
    public User getUserByUID(@PathVariable("account_id") String account_id) {
        try {
            return userService.getUserByAccountId(account_id);
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> addNewUser(@RequestBody @Valid UserDTO userDTO) {
        try {
            return userService.addNewUser(userDTO);
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @DeleteMapping(path = "{userId}")
    public void deleteUser(@PathVariable("userId") Long userId) {
        userService.deleteUser(userId);
    }
}
