package com.example.JewelryShop.configs;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;

public class AuditAware implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("lucifer");
    }
}