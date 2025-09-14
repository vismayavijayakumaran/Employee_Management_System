package com.example.employeemanagement.service;

import com.example.employeemanagement.DTO.DepartmentRequest;
import com.example.employeemanagement.DTO.DepartmentResponse;
import com.example.employeemanagement.DTO.DepartmentWithEmployeesResponse;
import com.example.employeemanagement.DTO.EmployeeResponse;
import com.example.employeemanagement.Entity.Department;
import com.example.employeemanagement.Entity.Employee;
import com.example.employeemanagement.exception.DepartmentServiceException;
import com.example.employeemanagement.repository.DepartmentRepository;
import java.util.UUID;
import java.util.List;
import java.util.stream.Collectors;

import com.example.employeemanagement.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public DepartmentResponse createDepartment(Department department) {
        try {
            log.info("Creating department: {}", department.getName());
            if (departmentRepository.existsByName(department.getName())) {
                throw new DepartmentServiceException("Department name already exists");
            }
            Department savedDepartment = departmentRepository.save(department);
            return savedDepartment.toDto();
        } catch (Exception e) {
            log.error("Error creating department: {}", e.getMessage());
            throw new DepartmentServiceException("Failed to create department: " + e.getMessage());
        }
    }

    public DepartmentResponse updateDepartment(UUID id, DepartmentRequest departmentDetails) {
        try {
            Department department = departmentRepository.findById(id)
                    .orElseThrow(() -> new DepartmentServiceException("Department not found"));
            department.setName(departmentDetails.getName());

            if (departmentDetails.getDepartmentHead() != null) {
                UUID headId = departmentDetails.getDepartmentHead();
                var employeeOpt = employeeRepository.findById(headId);
                if (employeeOpt.isEmpty()) {
                    throw new DepartmentServiceException("Department head not found with ID: " + headId);
                }
                department.setDepartmentHead(employeeOpt.get());
            }

            Department updatedDepartment = departmentRepository.save(department);
            return updatedDepartment.toDto();
        } catch (DepartmentServiceException e) {
            log.error("DepartmentServiceException: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error while updating department: {}", e.getMessage());
            throw new DepartmentServiceException("Failed to update department: " + e.getMessage());
        }
    }

    public void deleteDepartment(UUID id) {
        try {
            Department department = departmentRepository.findById(id)
                    .orElseThrow(() -> new DepartmentServiceException("Department not found"));
            if (employeeRepository.existsByDepartmentId(id)) {
                throw new DepartmentServiceException("Cannot delete department with assigned employees");
            }
            departmentRepository.deleteById(id);
        } catch (DepartmentServiceException e) {
            log.error("DepartmentServiceException: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error while deleting department: {}", e.getMessage());
            throw new DepartmentServiceException("Failed to delete department: " + e.getMessage());
        }
    }

    public Page<DepartmentResponse> getAllDepartments(Pageable pageable) {
        try {
            log.info("Fetching all departments with pagination");
            Page<Department> departments = departmentRepository.findAll(pageable);
            return departments.map(Department::toDto);
        } catch (Exception e) {
            log.error("Error fetching departments: {}", e.getMessage());
            throw new DepartmentServiceException("Failed to fetch departments: " + e.getMessage());
        }
    }

    public DepartmentWithEmployeesResponse getDepartmentWithEmployees(UUID id) {
        try {
            log.info("Fetching department with employees for department ID: {}", id);
            Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new DepartmentServiceException("Department not found"));
            List<EmployeeResponse> employees = employeeRepository.findByDepartmentId(id)
                .stream()
                .map(Employee::toDto)
                .collect(Collectors.toList());
            DepartmentWithEmployeesResponse response = new DepartmentWithEmployeesResponse();
            response.setId(department.getId());
            response.setName(department.getName());
            response.setEmployees(employees);
            return response;
        } catch (DepartmentServiceException e) {
            log.error("DepartmentServiceException: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error while fetching department with employees: {}", e.getMessage());
            throw new DepartmentServiceException("Failed to fetch department with employees: " + e.getMessage());
        }
    }

    public DepartmentResponse getDepartmentById(UUID id) {
        try {
            log.info("Fetching department by ID: {}", id);
            Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new DepartmentServiceException("Department not found"));
            return department.toDto();
        } catch (DepartmentServiceException e) {
            log.error("DepartmentServiceException: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error while fetching department by ID: {}", e.getMessage());
            throw new DepartmentServiceException("Failed to fetch department: " + e.getMessage());
        }
    }
}