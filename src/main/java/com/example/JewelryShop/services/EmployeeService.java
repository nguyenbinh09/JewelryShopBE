package com.example.JewelryShop.services;

import com.example.JewelryShop.exceptions.NotFoundException;
import com.example.JewelryShop.models.Employee;
import com.example.JewelryShop.models.Role;
import com.example.JewelryShop.models.User;
import com.example.JewelryShop.repositories.EmployeeRepository;
import com.example.JewelryShop.repositories.RoleRepository;
import com.example.JewelryShop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    public String generateEmployeeCode(Role role, Employee employee) {
        String PREFIX = role.getCode();

        String idPart = String.format("%04d", employee.getId()); // Zero-padded ID
        return PREFIX + idPart;
    }

    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElseThrow(() -> new NotFoundException("Employee with id " + id + " does not exist"));
    }

    public void addEmployee(Long userId, Long RoleId) {
        Employee employee = new Employee();
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User with id " + userId + " does not exist"));
        employee.setUser(user);
        Role role = roleRepository.findById(RoleId).orElseThrow(() -> new NotFoundException("Role with id " + RoleId + " does not exist"));
        employee.setRole(role);
        Employee employeeSaved = employeeRepository.save(employee);
        employeeSaved.setCode(generateEmployeeCode(role, employeeSaved));
        employeeRepository.save(employeeSaved);
    }
}
