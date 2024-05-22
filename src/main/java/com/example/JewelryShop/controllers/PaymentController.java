package com.example.JewelryShop.controllers;

import com.example.JewelryShop.dtos.IpnMoMoWebhookDTO;
import com.example.JewelryShop.services.MoMoPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private MoMoPaymentService moMoPaymentService;

    @PostMapping("/create")
    public String createPayment() {
        try {
            return moMoPaymentService.createPayment();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    @PostMapping("/ipn_momo_webhook")
    public ResponseEntity<?> ipnMoMoWebhook(@RequestBody IpnMoMoWebhookDTO ipnMoMoWebhookDTO) {
        try {
            return moMoPaymentService.ipnMoMoWebhook(ipnMoMoWebhookDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}
