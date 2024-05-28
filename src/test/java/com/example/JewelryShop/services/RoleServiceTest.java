package com.example.JewelryShop.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.JewelryShop.dtos.RoleDTO;
import com.example.JewelryShop.exceptions.NotFoundException;
import com.example.JewelryShop.models.Role;
import com.example.JewelryShop.repositories.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleService roleService;

    @Test
    void getRoles_ReturnsRoleList() {
        List<Role> roles = List.of(new Role(), new Role());
        when(roleRepository.findAll()).thenReturn(roles);

        List<Role> result = roleService.getRoles();

        assertEquals(roles.size(), result.size());
        verify(roleRepository, times(1)).findAll();
    }

    @Test
    void getRoleById_ValidId_ReturnsRole() {
        Long roleId = 1L;
        Role role = new Role();
        when(roleRepository.findById(roleId)).thenReturn(Optional.of(role));

        Role result = roleService.getRoleById(roleId);

        assertEquals(role, result);
        verify(roleRepository, times(1)).findById(roleId);
    }

    @Test
    void getRoleById_InvalidId_ThrowsNotFoundException() {
        Long roleId = 1L;
        when(roleRepository.findById(roleId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> roleService.getRoleById(roleId));
        verify(roleRepository, times(1)).findById(roleId);
    }

    @Test
    void addNewRole_ValidInput_ReturnsOkResponse() {
        RoleDTO roleDTO = mock(RoleDTO.class);
        Role role = new Role();
        when(roleDTO.toEntity()).thenReturn(role);

        ResponseEntity<?> response = roleService.addNewRole(roleDTO);

        assertEquals(ResponseEntity.ok("Role added successfully"), response);
        verify(roleRepository, times(1)).save(role);
    }

    @Test
    void updateRole_ValidId_ReturnsOkResponse() {
        Long roleId = 1L;
        RoleDTO roleDTO = mock(RoleDTO.class);
        Role role = new Role();
        when(roleRepository.findById(roleId)).thenReturn(Optional.of(role));

        ResponseEntity<?> response = roleService.updateRole(roleId, roleDTO);

        assertEquals(ResponseEntity.ok("Role updated successfully"), response);
        verify(roleRepository, times(1)).findById(roleId);
        verify(roleRepository, times(1)).save(role);
    }

    @Test
    void updateRole_InvalidId_ThrowsNotFoundException() {
        Long roleId = 1L;
        RoleDTO roleDTO = mock(RoleDTO.class);
        when(roleRepository.findById(roleId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> roleService.updateRole(roleId, roleDTO));
        verify(roleRepository, times(1)).findById(roleId);
        verify(roleRepository, times(0)).save(any(Role.class));
    }

    @Test
    void deleteRole_ValidId_ReturnsOkResponse() {
        Long roleId = 1L;
        Role role = new Role();
        when(roleRepository.findById(roleId)).thenReturn(Optional.of(role));

        ResponseEntity<?> response = roleService.deleteRole(roleId);

        assertEquals(ResponseEntity.ok("Role deleted successfully"), response);
        assertTrue(role.getIs_deleted());
        verify(roleRepository, times(1)).findById(roleId);
        verify(roleRepository, times(1)).save(role);
    }

    @Test
    void deleteRole_InvalidId_ThrowsNotFoundException() {
        Long roleId = 1L;
        when(roleRepository.findById(roleId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> roleService.deleteRole(roleId));
        verify(roleRepository, times(1)).findById(roleId);
        verify(roleRepository, times(0)).save(any(Role.class));
    }
}

