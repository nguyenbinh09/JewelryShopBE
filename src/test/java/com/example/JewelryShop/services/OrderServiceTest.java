package com.example.JewelryShop.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.JewelryShop.dtos.ContactDTO;
import com.example.JewelryShop.dtos.OrderDTO;
import com.example.JewelryShop.dtos.OrderDetailDTO;
import com.example.JewelryShop.exceptions.NotFoundException;
import com.example.JewelryShop.models.*;
import com.example.JewelryShop.repositories.CustomerRepository;
import com.example.JewelryShop.repositories.OrderRepository;
import com.example.JewelryShop.repositories.PaymentMethodRepository;
import com.example.JewelryShop.repositories.VariantRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private VariantRepository variantRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private PaymentMethodRepository paymentMethodRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void getOrders_ReturnsListOfOrders() {
        // Arrange
        Order order = new Order();
        when(orderRepository.findAll()).thenReturn(List.of(order));

        // Act
        List<Order> result = orderService.getOrders();

        // Assert
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void getOrderById_ExistingId_ReturnsOrder() {
        // Arrange
        Long id = 1L;
        Order order = new Order();
        when(orderRepository.findById(id)).thenReturn(Optional.of(order));

        // Act
        Order result = orderService.getOrderById(id);

        // Assert
        assertNotNull(result);
        assertEquals(order, result);
        verify(orderRepository, times(1)).findById(id);
    }

    @Test
    void getOrderById_NonExistingId_ThrowsNotFoundException() {
        // Arrange
        Long id = 1L;
        when(orderRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> orderService.getOrderById(id));
        verify(orderRepository, times(1)).findById(id);
    }

    @Test
    void addNewOrder_ValidInput_ReturnsOkResponse() {
        // Arrange
        OrderDTO orderDTO = mock(OrderDTO.class);
        Order order = new Order();
        when(orderDTO.toEntity()).thenReturn(order);
        when(orderDTO.getPurchaser()).thenReturn(1L);
        when(orderDTO.getPayment_method()).thenReturn(1L);

        Customer customer = new Customer();
        when(customerRepository.findById(orderDTO.getPurchaser())).thenReturn(Optional.of(customer));

        PaymentMethod paymentMethod = new PaymentMethod();
        when(paymentMethodRepository.findById(orderDTO.getPayment_method())).thenReturn(Optional.of(paymentMethod));

        when(orderDTO.getShipping_contact()).thenReturn(new ContactDTO());

        Variant variant = new Variant();
        variant.setQuantity(10);
        when(variantRepository.findById(anyLong())).thenReturn(Optional.of(variant));

        OrderDetailDTO orderDetailDTO = mock(OrderDetailDTO.class);
        when(orderDetailDTO.getVariant_id()).thenReturn(1L);
        when(orderDetailDTO.getQuantity()).thenReturn(5);
        List<OrderDetailDTO> orderDetails = List.of(orderDetailDTO);
        when(orderDTO.getOrdered_items()).thenReturn(orderDetails.toArray(new OrderDetailDTO[0]));

        OrderDetail orderDetail = mock(OrderDetail.class);
        when(orderDetailDTO.toEntity()).thenReturn(orderDetail);

        // Act
        Order response = orderService.addNewOrder(orderDTO);

        // Assert
        assertEquals(order, response);
        verify(orderRepository, times(1)).save(order);
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    void updateOrderStatus_ValidId_UpdatesStatus() {
        // Arrange
        Long id = 1L;
        String status = "Shipped";
        Order order = new Order();
        when(orderRepository.findById(id)).thenReturn(Optional.of(order));

        // Act
        ResponseEntity<?> response = orderService.updateOrderStatus(id, status);

        // Assert
        assertEquals(ResponseEntity.ok("Order status updated successfully"), response);
        assertEquals(status, order.getStatus());
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void deleteOrder_ExistingId_SetsIsDeletedTrue() {
        // Arrange
        Long id = 1L;
        Order order = new Order();
        OrderDetail orderDetail = new OrderDetail();
        Variant variant = new Variant();
        variant.setQuantity(5);
        orderDetail.setVariant(variant);
        orderDetail.setQuantity(3);
        order.setOrderDetail(List.of(orderDetail));
        when(orderRepository.findById(id)).thenReturn(Optional.of(order));

        // Act
        ResponseEntity<?> response = orderService.deleteOrder(id);

        // Assert
        assertEquals(ResponseEntity.ok("Order deleted successfully"), response);
        assertTrue(order.getIs_deleted());
        assertTrue(orderDetail.getIs_deleted());
        assertEquals(8, variant.getQuantity());
        verify(orderRepository, times(1)).save(order);
        verify(variantRepository, times(1)).save(variant);
    }
}

