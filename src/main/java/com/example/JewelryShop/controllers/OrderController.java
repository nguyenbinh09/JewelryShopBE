package com.example.JewelryShop.controllers;

import com.example.JewelryShop.dtos.OrderDTO;
import com.example.JewelryShop.exceptions.InternalServerErrorException;
import com.example.JewelryShop.exceptions.NotFoundException;
import com.example.JewelryShop.models.Order;
import com.example.JewelryShop.services.OrderService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "api/v1/order")
@Validated
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public List<Order> getOrders() {
        try {
            return orderService.getOrders();
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable("id") Long id) {
        try {
            return orderService.getOrderById(id);
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> addNewOrder(@RequestBody @Valid OrderDTO orderDTO) {
        try {
            return orderService.addNewOrder(orderDTO);
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }
}
