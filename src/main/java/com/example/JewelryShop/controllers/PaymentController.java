package com.example.JewelryShop.controllers;

import com.example.JewelryShop.dtos.IpnMoMoWebhookDTO;
import com.example.JewelryShop.dtos.PaymentMethodDTO;
import com.example.JewelryShop.exceptions.InternalServerErrorException;
import com.example.JewelryShop.exceptions.NotFoundException;
import com.example.JewelryShop.models.Order;
import com.example.JewelryShop.models.PaymentMethod;
import com.example.JewelryShop.services.MoMoPaymentService;
import com.example.JewelryShop.services.PaymentMethodService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payment")
@Validated
public class PaymentController {

    @Autowired
    private MoMoPaymentService moMoPaymentService;

    @Autowired
    private PaymentMethodService paymentMethodService;

    @PostMapping("/create_payment_by_momo/{order_id}")
    public String createPayment(@PathVariable Long order_id) {
        try {
            return moMoPaymentService.createPayment(order_id);
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @PostMapping("/ipn_momo_webhook")
    public String ipnMoMoWebhook(@RequestBody IpnMoMoWebhookDTO ipnMoMoWebhookDTO) {
        try {
            System.out.println("IPN MoMo Webhook: " + ipnMoMoWebhookDTO.getOrderId());
            return moMoPaymentService.ipnMoMoWebhook(ipnMoMoWebhookDTO);
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createPaymentMethod(@RequestBody @Valid PaymentMethodDTO paymentMethodDTO) {
        try {
            return paymentMethodService.createPaymentMethod(paymentMethodDTO);
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @GetMapping
    public List<PaymentMethod> getAllPaymentMethods() {
        try {
            return paymentMethodService.getAllPaymentMethods();
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public PaymentMethod getPaymentMethodById(@PathVariable Long id) {
        try {
            return paymentMethodService.getPaymentMethodById(id);
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePaymentMethod(@PathVariable Long id, @RequestBody PaymentMethodDTO paymentMethodDTO) {
        try {
            return paymentMethodService.updatePaymentMethod(id, paymentMethodDTO);
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePaymentMethod(@PathVariable Long id) {
        try {
            return paymentMethodService.deletePaymentMethod(id);
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }
}
