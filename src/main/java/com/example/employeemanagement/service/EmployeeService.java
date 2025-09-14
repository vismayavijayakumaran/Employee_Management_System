package com.example.employeemanagement.service;

import com.example.employeemanagement.DTO.EmployeeRequest;
import com.example.employeemanagement.DTO.EmployeeResponse;
import com.example.employeemanagement.DTO.UpdateDepartment;
import com.example.employeemanagement.DTO.EmployeeLookupResponse;
import com.example.employeemanagement.Entity.Department;
import com.example.employeemanagement.Entity.Employee;
import com.example.employeemanagement.exception.EmployeeException;
import com.example.employeemanagement.repository.DepartmentRepository;
import com.example.employeemanagement.repository.EmployeeRepository;
import java.util.UUID;
import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;
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
    @Autowired
    private DepartmentRepository departmentRepository;

    public EmployeeResponse createEmployee(Employee employee) {
        try {
            // Check if email already exists
            if (employee.getEmail() != null && employeeRepository.existsByEmail(employee.getEmail())) {
            log.error("Email already exists: {}", employee.getEmail());
            throw new EmployeeException("User already exists with this email");
            }
            // Check department
            if (employee.getDepartment() == null || employee.getDepartment().getId() == null ||
            !departmentRepository.existsById(employee.getDepartment().getId())) {
            log.error("Invalid or missing department for employee");
            throw new EmployeeException("Department not found");
            }
            // Check reporting manager (if provided)
            if (employee.getReportingManager() != null && employee.getReportingManager().getId() != null) {
            if (!employeeRepository.existsById(employee.getReportingManager().getId())) {
                log.error("Reporting manager not found with ID: {}", employee.getReportingManager().getId());
                throw new EmployeeException("Reporting manager not found");
            }
            }
            log.info("Creating employee: {}", employee.getName());
            Employee savedEmployee = employeeRepository.save(employee);
            return savedEmployee.toDto();
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

    public EmployeeResponse updateEmployee(UUID id, EmployeeRequest employeeDetails) {
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
            employee.setName(employeeDetails.getName() != null ? employeeDetails.getName() : employee.getName());
            employee.setDateOfBirth(employeeDetails.getDateOfBirth() != null ? employeeDetails.getDateOfBirth() : employee.getDateOfBirth());
            employee.setSalary(employeeDetails.getSalary() != null ? employeeDetails.getSalary() : employee.getSalary());
            if (employeeDetails.getDepartmentID() != null) {
                // Validate department exists
                Optional<Department> departmentOptional = departmentRepository.findById(employeeDetails.getDepartmentID());
                if (!departmentOptional.isPresent()) {
                    log.error("Department not found with ID: {}", employeeDetails.getDepartmentID());
                    throw new EmployeeException("Department not found");
                }
                employee.setDepartment(departmentOptional.get());
            }
            employee.setAddress(employeeDetails.getAddress() != null ? employeeDetails.getAddress() : employee.getAddress());
            employee.setRole(employeeDetails.getRole() != null ? employeeDetails.getRole() : employee.getRole());
            employee.setJoiningDate(employeeDetails.getJoiningDate() != null ? employeeDetails.getJoiningDate() : employee.getJoiningDate());
            employee.setYearlyBonusPercentage(employeeDetails.getYearlyBonusPercentage() != null ? employeeDetails.getYearlyBonusPercentage() : employee.getYearlyBonusPercentage());
            if (employeeDetails.getReportingManagerId() != null) {
                Optional<Employee> managerOptional = employeeRepository.findById(employeeDetails.getReportingManagerId());
                if (!managerOptional.isPresent()) {
                    log.error("Reporting manager not found with ID: {}", employeeDetails.getReportingManagerId());
                    throw new EmployeeException("Reporting manager not found");
                }
                employee.setReportingManager(managerOptional.get());
            } 
            employeeRepository.save(employee);
            return employee.toDto();
            
        } catch (EmployeeException e) {
            log.error("Employee error: {}", e.getMessage());
            throw e; // Let global exception handler handle this
        } catch (Exception e) {
            log.error("Error updating employee: {}", e.getMessage());
            throw new RuntimeException("Failed to update employee", e);
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

    public EmployeeResponse updateEmployeeDepartment(UUID employeeId, UpdateDepartment request) {
        try {
            Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeException("Employee not found"));
            Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new EmployeeException("Department not found"));
            employee.setDepartment(department);
            Employee updatedEmployee = employeeRepository.save(employee);
            return updatedEmployee.toDto();
        } catch (EmployeeException e) {
            log.error("EmployeeException: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error while updating employee department: {}", e.getMessage());
            throw new EmployeeException("Failed to update employee department: " + e.getMessage());
        }
    }

    public Page<EmployeeLookupResponse> getEmployeeLookups(Pageable pageable) {
        try {
            log.info("Fetching employee lookup list (ID and Name only) with pagination: {}", pageable);
            Page<Employee> employeePage = employeeRepository.findAll(pageable);
            return employeePage.map(emp -> {
                EmployeeLookupResponse dto = new EmployeeLookupResponse();
                dto.setId(emp.getId());
                dto.setName(emp.getName()); // or combine first/last name if needed
                return dto;
            });
        } catch (Exception e) {
            log.error("Error fetching employee lookup list: {}", e.getMessage());
            throw new EmployeeException("Failed to fetch employee lookup list: " + e.getMessage());
        }
    }
}
