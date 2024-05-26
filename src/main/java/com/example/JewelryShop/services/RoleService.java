package com.example.JewelryShop.services;

import com.example.JewelryShop.dtos.RoleDTO;
import com.example.JewelryShop.exceptions.NotFoundException;
import com.example.JewelryShop.models.Role;
import com.example.JewelryShop.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    public Role getRoleById(Long roleId) {
        return roleRepository.findById(roleId).orElseThrow(() -> new NotFoundException("Role with id " + roleId + " not found"));
    }

    public ResponseEntity<?> addNewRole(RoleDTO roleDTO) {
        Role role = roleDTO.toEntity();
        roleRepository.save(role);
        return ResponseEntity.ok("Role added successfully");
    }

    public ResponseEntity<?> updateRole(Long roleId, RoleDTO roleDTO) {
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new NotFoundException("Role with id " + roleId + " not found"));
        roleDTO.toEntity(role); // Update the role with the new data (roleDTO)
        roleRepository.save(role);
        return ResponseEntity.ok("Role updated successfully");
    }

    public ResponseEntity<?> deleteRole(Long roleId) {
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new NotFoundException("Role with id " + roleId + " not found"));
        role.setIs_deleted(true);
        roleRepository.save(role);
        return ResponseEntity.ok("Role deleted successfully");
    }
}
