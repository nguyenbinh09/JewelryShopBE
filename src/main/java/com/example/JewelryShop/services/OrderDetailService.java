package com.example.JewelryShop.services;

import com.example.JewelryShop.models.OrderDetail;
import com.example.JewelryShop.repositories.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
@Validated
public class OrderDetailService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    public List<OrderDetail> getOrderDetailByOrderId(Long orderId) {
        return orderDetailRepository.findByOrderId(orderId);
    }
}
