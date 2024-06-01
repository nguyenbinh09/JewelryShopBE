package com.example.JewelryShop.services;

import com.example.JewelryShop.exceptions.NotFoundException;
import com.example.JewelryShop.models.Employee;
import com.example.JewelryShop.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElseThrow(() -> new NotFoundException("Employee with id " + id + " does not exist"));
    }
}
