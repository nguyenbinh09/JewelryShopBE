package com.example.JewelryShop.services;

import com.example.JewelryShop.dtos.PaymentMethodDTO;
import com.example.JewelryShop.exceptions.NotFoundException;
import com.example.JewelryShop.models.PaymentMethod;
import com.example.JewelryShop.repositories.PaymentMethodRepository;
import jakarta.persistence.Access;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
public class PaymentMethodService {
    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    public List<PaymentMethod> getAllPaymentMethods() {
        return paymentMethodRepository.findAll();
    }

    public PaymentMethod getPaymentMethodById(Long id) {
        return paymentMethodRepository.findById(id).orElseThrow(() -> new NotFoundException("Payment method with id " + id + " not found"));
    }

    public ResponseEntity<?> createPaymentMethod(@Valid PaymentMethodDTO paymentMethodDTO) {
        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setType(paymentMethodDTO.getType());
        paymentMethodRepository.save(paymentMethod);
        return ResponseEntity.ok("Payment method created successfully");
    }

    public ResponseEntity<?> deletePaymentMethod(Long id) {
        PaymentMethod paymentMethod = paymentMethodRepository.findById(id).orElseThrow(() -> new NotFoundException("Payment method with id " + id + " not found"));
        paymentMethod.setIs_deleted(true);
        paymentMethodRepository.save(paymentMethod);
        return ResponseEntity.ok("Payment method deleted successfully");
    }

    public ResponseEntity<?> updatePaymentMethod(Long id, PaymentMethodDTO paymentMethodDTO) {
        PaymentMethod paymentMethod = paymentMethodRepository.findById(id).orElseThrow(() -> new NotFoundException("Payment method with id " + id + " not found"));
        if (paymentMethodDTO.getType() != null) {
            paymentMethod.setType(paymentMethodDTO.getType());
        }
        paymentMethodRepository.save(paymentMethod);
        return ResponseEntity.ok("Payment method updated successfully");
    }
}
