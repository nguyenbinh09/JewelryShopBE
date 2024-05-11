package com.example.JewelryShop.services;

import com.example.JewelryShop.dtos.OrderDTO;
import com.example.JewelryShop.dtos.OrderDetailDTO;
import com.example.JewelryShop.exceptions.BadRequestException;
import com.example.JewelryShop.exceptions.NotFoundException;
import com.example.JewelryShop.models.*;
import com.example.JewelryShop.repositories.CustomerRepository;
import com.example.JewelryShop.repositories.JewelryItemRepository;
import com.example.JewelryShop.repositories.OrderDetailRepository;
import com.example.JewelryShop.repositories.OrderRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Validated
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private JewelryItemRepository jewelryItemRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private ModelMapper modelMapper;

    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isEmpty())
            throw new NotFoundException("Could not find order with id " + id);
        return order.get();
    }

    @Transactional
    public ResponseEntity<?> addNewOrder(@Valid OrderDTO orderDTO) {
        Order order = orderDTO.toEntity();
        List<OrderDetail> listOrderDetail = new ArrayList<>();
        for (OrderDetailDTO orderDetailDTO : orderDTO.getOrdered_items()) {
            OrderDetail orderDetail = orderDetailDTO.toEntity();
            Optional<JewelryItem> item = jewelryItemRepository.findById(orderDetailDTO.getJewelry_item_id());
            if (item.isEmpty()) {
                throw new NotFoundException("Could not find item with id " + orderDetailDTO.getJewelry_item_id());
            }
            int quantity = item.get().getQuantity() - orderDetailDTO.getQuantity();
            if (quantity < 0) {
                throw new BadRequestException("Not enough quantity for item with SKU CODE " + item.get().getSku_code());
            } else {
                item.get().setQuantity(quantity);
                jewelryItemRepository.save(item.get());
                orderDetail.setJewelry_item(item.get());
                orderDetail.setOrder(order);
                listOrderDetail.add(orderDetail);
            }
        }
//        List<OrderDetail> list = orderDetailRepository.saveAll(listOrderDetail);
        order.setOrderDetail(listOrderDetail);
//        Customer customer = customerRepository.findById(orderDTO.getPurchaser()).orElseThrow(() -> new NotFoundException("Could not find customer with id " + orderDTO.getPurchaser()));
//        order.setCustomer(customer);


        Contact shippingContact = orderDTO.getShipping_contact().toEntity();
        order.setContact(shippingContact);
        orderRepository.save(order);
        return ResponseEntity.ok("Order created successfully");
    }
}
