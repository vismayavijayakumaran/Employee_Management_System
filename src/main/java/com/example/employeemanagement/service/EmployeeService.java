package com.example.employeemanagement.service;

import com.example.employeemanagement.model.Employee;
import com.example.employeemanagement.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    public Employee updateEmployee(Long id, Employee employeeDetails) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Employee not found"));
        employee.setName(employeeDetails.getName());
        employee.setDateOfBirth(employeeDetails.getDateOfBirth());
        employee.setSalary(employeeDetails.getSalary());
        employee.setDepartment(employeeDetails.getDepartment());
        employee.setAddress(employeeDetails.getAddress());
        employee.setRole(employeeDetails.getRole());
        employee.setJoiningDate(employeeDetails.getJoiningDate());
        employee.setYearlyBonusPercentage(employeeDetails.getYearlyBonusPercentage());
        employee.setReportingManager(employeeDetails.getReportingManager());
        return employeeRepository.save(employee);
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    public Page<Employee> getAllEmployees( Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }
}