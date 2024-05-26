package com.example.JewelryShop.repositories;

import com.example.JewelryShop.models.PersonalInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InformationRepository extends JpaRepository<PersonalInformation, Long> {
}
