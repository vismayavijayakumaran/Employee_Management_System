package com.example.employeemanagement.controller;

import com.example.employeemanagement.DTO.EmployeeRequest;
import com.example.employeemanagement.DTO.EmployeeResponse;
import com.example.employeemanagement.DTO.UpdateDepartment;
import com.example.employeemanagement.Entity.Employee;
import com.example.employeemanagement.service.EmployeeService;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<EmployeeResponse> createEmployee(@RequestBody Employee employee) {
        EmployeeResponse createdEmployee = employeeService.createEmployee(employee);
        return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAllEmployees(
        @RequestParam(value = "lookup", required = false) Boolean lookup,
        @PageableDefault(page = 0, size = 20, sort = {"createdAt"}, direction = Direction.DESC) Pageable pageable) {
        if (Boolean.TRUE.equals(lookup)) {
            // Return only id and name
            return ResponseEntity.ok(employeeService.getEmployeeLookups(pageable));
        } else {
            Page<EmployeeResponse> employees = employeeService.getAllEmployees(pageable);
            return ResponseEntity.ok(employees);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponse> updateEmployee(@PathVariable UUID id, @RequestBody EmployeeRequest employee) {
        EmployeeResponse updatedEmployee = employeeService.updateEmployee(id, employee);
        return ResponseEntity.ok(updatedEmployee);
    }

    @PatchMapping("/{id}/department")
    public ResponseEntity<EmployeeResponse> updateEmployeeDepartment(
            @PathVariable UUID id,
            @RequestBody UpdateDepartment request) {
        EmployeeResponse updatedEmployee = employeeService.updateEmployeeDepartment(id, request);
        return ResponseEntity.ok(updatedEmployee);
    }
}