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
    public Order addNewOrder(@RequestBody @Valid OrderDTO orderDTO) {
        try {
            return orderService.addNewOrder(orderDTO);
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateOrderStatus(@PathVariable("id") Long id, @RequestBody @Valid String status) {
        try {
            return orderService.updateOrderStatus(id, status);
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable("id") Long id) {
        try {
            return orderService.deleteOrder(id);
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }
}
