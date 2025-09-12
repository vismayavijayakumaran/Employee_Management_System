package com.example.employeemanagement.service;

import com.example.employeemanagement.DTO.EmployeeResponse;
import com.example.employeemanagement.Entity.Employee;
import com.example.employeemanagement.repository.EmployeeRepository;
import java.util.UUID;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee createEmployee(Employee employee) {
        try {
            if (employee == null) {
                log.error("Attempted to create a null employee");
                throw new IllegalArgumentException("Employee object cannot be null");
            }
            log.info("Creating employee: {}", employee.getName());
            return employeeRepository.save(employee);
        } catch (Exception e) {
            log.error("Error creating employee: {}", e.getMessage());
            throw new RuntimeException("Failed to create employee", e);
        }
    }

    public Optional<Employee> getEmployeeById(UUID id) {
        try {
            if (id == null) {
                log.error("Employee ID is null");
                throw new IllegalArgumentException("Employee ID cannot be null");
            }
            log.info("Fetching employee with ID: {}", id);
            return employeeRepository.findById(id);
        } catch (Exception e) {
            log.error("Error fetching employee: {}", e.getMessage());
            throw new RuntimeException("Failed to fetch employee", e);
        }
    }

    public Employee updateEmployee(UUID id, Employee employeeDetails) {
        try {
            if (id == null || employeeDetails == null) {
                log.error("Invalid input for updating employee");
                throw new IllegalArgumentException("Employee ID and details cannot be null");
            }
            Employee employee = employeeRepository.findById(id)
                    .orElseThrow(() -> {
                        log.error("Employee not found with ID: {}", id);
                        return new RuntimeException("Employee not found");
                    });
            log.info("Updating employee with ID: {}", id);
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
        } catch (Exception e) {
            log.error("Error updating employee: {}", e.getMessage());
            throw new RuntimeException("Failed to update employee", e);
        }
    }

    public void deleteEmployee(UUID id) {
        try {
            if (id == null) {
                log.error("Employee ID is null for deletion");
                throw new IllegalArgumentException("Employee ID cannot be null");
            }
            if (!employeeRepository.existsById(id)) {
                log.error("Employee not found with ID: {}", id);
                throw new RuntimeException("Employee not found");
            }
            log.info("Deleting employee with ID: {}", id);
            employeeRepository.deleteById(id);
        } catch (Exception e) {
            log.error("Error deleting employee: {}", e.getMessage());
            throw new RuntimeException("Failed to delete employee", e);
        }
    }

    public Page<EmployeeResponse> getAllEmployees(Pageable pageable) {
        try {
            log.info("Fetching all employees with pagination: {}", pageable);
            Page<Employee> employeePage = employeeRepository.findAll(pageable);
            return employeePage.map(Employee::toDto);
        } catch (Exception e) {
            log.error("Error fetching employees: {}", e.getMessage());
            throw new RuntimeException("Failed to fetch employees", e);
        }
    }
}
