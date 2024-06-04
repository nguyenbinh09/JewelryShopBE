package com.example.JewelryShop.services;

import com.example.JewelryShop.dtos.OrderDTO;
import com.example.JewelryShop.dtos.OrderDetailDTO;
import com.example.JewelryShop.exceptions.BadRequestException;
import com.example.JewelryShop.exceptions.NotFoundException;
import com.example.JewelryShop.models.*;
import com.example.JewelryShop.repositories.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Validated
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private VariantRepository variantRepository;
    @Autowired
    private PaymentMethodRepository paymentMethodRepository;
    @Autowired
    private ModelMapper modelMapper;

    public static String generateOrderCode(Order order) {
        String PREFIX = "ORD";
        int RANDOM_BOUND = 1000;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String timestamp = dateFormat.format(new Date());

        String idPart = String.format("%04d", order.getId()); // Zero-padded ID
        System.out.println(PREFIX + timestamp + idPart);
        return PREFIX + timestamp + idPart;
    }

    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderByCode(String code) {
        return orderRepository.findByOrderCode(code).orElseThrow(() -> new NotFoundException("Could not find order with code " + code));
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Could not find order with id " + id));
    }

    @Transactional
    public Order addNewOrder(@Valid OrderDTO orderDTO) {
        Order order = orderDTO.toEntity();
        List<OrderDetail> listOrderDetail = new ArrayList<>();
        for (OrderDetailDTO orderDetailDTO : orderDTO.getOrdered_items()) {
            OrderDetail orderDetail = orderDetailDTO.toEntity();
            Optional<Variant> variantItem = variantRepository.findById(orderDetailDTO.getVariant_id());
            if (variantItem.isEmpty()) {
                throw new NotFoundException("Could not find item with variant id " + orderDetailDTO.getVariant_id());
            }
            int quantity = variantItem.get().getQuantity() - orderDetailDTO.getQuantity();
            if (quantity < 0) {
                throw new BadRequestException("Not enough quantity for item with variant id " + variantItem.get().getId());
            } else {
                variantItem.get().setQuantity(quantity);
                variantRepository.save(variantItem.get());
                orderDetail.setVariant(variantItem.get());
                orderDetail.setOrder(order);
                listOrderDetail.add(orderDetail);
            }
            orderDetail.setQuantity(orderDetailDTO.getQuantity());
        }
        order.setOrderDetail(listOrderDetail);
        Customer customer = customerRepository.findById(orderDTO.getPurchaser()).orElseThrow(() -> new NotFoundException("Could not find customer with id " + orderDTO.getPurchaser()));
        order.setCustomer(customer);
        Contact shippingContact = orderDTO.getShipping_contact().toEntity();
        order.setContact(shippingContact);
        PaymentMethod paymentMethod = paymentMethodRepository.findById(orderDTO.getPayment_method()).orElseThrow(() -> new NotFoundException("Could not find payment method with id " + orderDTO.getPayment_method()));
        order.setPaymentMethod(paymentMethod);
        Order savedOrder = orderRepository.save(order);
        order.setCode(generateOrderCode(savedOrder));
        orderRepository.save(savedOrder);
        customer.getOrder_history().add(savedOrder);
        customerRepository.save(customer);
        return savedOrder;
    }

    public ResponseEntity<?> updateOrderStatus(Long id, @Valid String status) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Could not find order with id " + id));
        order.setStatus(status);
        orderRepository.save(order);
        return ResponseEntity.ok("Order status updated successfully");
    }

    @Transactional
    public ResponseEntity<?> deleteOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Could not find order with id " + id));
        order.setIs_deleted(true);
        for (OrderDetail orderDetail : order.getOrderDetail()) {
            Variant variantItem = orderDetail.getVariant();
            variantItem.setQuantity(variantItem.getQuantity() + orderDetail.getQuantity());
            orderDetail.setIs_deleted(true);
            variantRepository.save(variantItem);
        }
        orderRepository.save(order);
        return ResponseEntity.ok("Order deleted successfully");
    }
}
