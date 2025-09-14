package com.example.employeemanagement.controller;

import com.example.employeemanagement.DTO.DepartmentRequest;
import com.example.employeemanagement.DTO.DepartmentResponse;
import com.example.employeemanagement.DTO.DepartmentWithEmployeesResponse;
import com.example.employeemanagement.Entity.Department;
import com.example.employeemanagement.service.DepartmentService;
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
@RequestMapping("/api/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @PostMapping
    public ResponseEntity<DepartmentResponse> createDepartment(@RequestBody Department department) {
        DepartmentResponse createdDepartment = departmentService.createDepartment(department);
        return new ResponseEntity<>(createdDepartment, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<DepartmentResponse>> getAllDepartments(
        @PageableDefault(page = 0, size = 20, sort = {"createdAt"}, direction = Direction.DESC) Pageable pageable
    ) {
        Page<DepartmentResponse> departments = departmentService.getAllDepartments(pageable);
        return ResponseEntity.ok(departments);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartmentResponse> updateDepartment(@PathVariable UUID id, @RequestBody DepartmentRequest department) {
        DepartmentResponse updatedDepartment = departmentService.updateDepartment(id, department);
        return ResponseEntity.ok(updatedDepartment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable UUID id) {
        departmentService.deleteDepartment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDepartmentById(
            @PathVariable UUID id,
            @RequestParam(value = "expand", required = false) String expand) {
        if ("employee".equalsIgnoreCase(expand)) {
            DepartmentWithEmployeesResponse response = departmentService.getDepartmentWithEmployees(id);
            return ResponseEntity.ok(response);
        } else {
            DepartmentResponse response = departmentService.getDepartmentById(id);
            return ResponseEntity.ok(response);
        }
    }
}