package com.example.JewelryShop.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.JewelryShop.dtos.UserDTO;
import com.example.JewelryShop.exceptions.BadRequestException;
import com.example.JewelryShop.models.Contact;
import com.example.JewelryShop.models.PersonalInformation;
import com.example.JewelryShop.models.User;
import com.example.JewelryShop.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private UserService userService;

    @Test
    void getUsers_ReturnsUserList() {
        // Arrange
        List<User> users = List.of(new User(), new User());
        when(userRepository.findAll()).thenReturn(users);

        // Act
        List<User> result = userService.getUsers();

        // Assert
        assertEquals(users, result);
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getUserByAccountId_ValidId_ReturnsUser() {
        // Arrange
        String accountId = "validAccountId";
        User user = new User();
        when(userRepository.findUserByAccountId(accountId)).thenReturn(user);

        // Act
        User result = userService.getUserByAccountId(accountId);

        // Assert
        assertEquals(user, result);
        verify(userRepository, times(1)).findUserByAccountId(accountId);
    }

    @Test
    void getUserByAccountId_InvalidId_ThrowsException() {
        // Arrange
        String accountId = "invalidAccountId";
        when(userRepository.findUserByAccountId(accountId)).thenReturn(null);

        // Act & Assert
        Exception exception = assertThrows(BadRequestException.class, () -> {
            userService.getUserByAccountId(accountId);
        });
        assertEquals("User with account id " + accountId + " does not exist", exception.getMessage());
    }

    @Test
    void addNewUser_ValidInput_ReturnsOkResponse() {
        // Arrange
        UserDTO userDTO = mock(UserDTO.class);
        User user = new User();
        when(userDTO.toEntity()).thenReturn(user);

        // Act
        ResponseEntity<?> response = userService.addNewUser(userDTO);

        // Assert
        assertEquals(ResponseEntity.ok("User created successfully"), response);
        verify(userRepository, times(1)).save(user);
        verify(customerService, times(1)).addNewCustomer(user.getId());
    }

    @Test
    void deleteUser_ValidId_ReturnsOkResponse() {
        // Arrange
        Long userId = 1L;
        User user = new User();
        PersonalInformation info = new PersonalInformation();
        Contact contact = new Contact();
        info.setContact(contact);
        user.setInformation(info);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        ResponseEntity<?> response = userService.deleteUser(userId);

        // Assert
        assertEquals(ResponseEntity.ok("User deleted successfully"), response);
        assertTrue(user.getIs_deleted());
        assertTrue(user.getInformation().getIs_deleted());
        assertTrue(user.getInformation().getContact().getIs_deleted());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void deleteUser_InvalidId_ThrowsException() {
        // Arrange
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(BadRequestException.class, () -> {
            userService.deleteUser(userId);
        });
        assertEquals("User with id " + userId + " does not exist", exception.getMessage());
    }

    @Test
    void addEmployee_ValidId_ReturnsOkResponse() {
        // Arrange
        Long userId = 1L;
        User user = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        Long roleId = 1L;

        // Act
        ResponseEntity<?> response = userService.addEmployee(userId, roleId);

        // Assert
        assertEquals(ResponseEntity.ok("Employee added successfully"), response);
        assertTrue(user.getIs_employee());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void addEmployee_InvalidId_ThrowsException() {
        // Arrange
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Long roleId = 1L;
        // Act & Assert
        Exception exception = assertThrows(BadRequestException.class, () -> {
            userService.addEmployee(userId, roleId);
        });
        assertEquals("User with id " + userId + " does not exist", exception.getMessage());
    }
}

