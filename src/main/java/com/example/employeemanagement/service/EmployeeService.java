package com.example.employeemanagement.service;

import com.example.employeemanagement.DTO.EmployeeResponse;
import com.example.employeemanagement.Entity.Employee;
import com.example.employeemanagement.repository.EmployeeRepository;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

//    public Optional<Employee> getEmployeeById(UUID id) {
//        return employeeRepository.findById(id);
//    }

    public Employee updateEmployee(UUID id, Employee employeeDetails) {
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

    public void deleteEmployee(UUID id) {
        employeeRepository.deleteById(id);
    }

    public Page<EmployeeResponse> getAllEmployees(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }
}