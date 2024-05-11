package com.example.JewelryShop.controllers;

import com.example.JewelryShop.models.Order;
import com.example.JewelryShop.models.OrderDetail;
import com.example.JewelryShop.services.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "api/v1/order-detail")
public class OrderDetailController {
    @Autowired
    private OrderDetailService orderDetailService;

    @GetMapping("/order/{orderId}")
    public List<OrderDetail> getOrderDetailByOrderId(@PathVariable Long orderId) {
        return orderDetailService.getOrderDetailByOrderId(orderId);
    }
}
