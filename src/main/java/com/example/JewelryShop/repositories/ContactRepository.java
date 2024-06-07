package com.example.JewelryShop.repositories;

import com.example.JewelryShop.models.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {
}
