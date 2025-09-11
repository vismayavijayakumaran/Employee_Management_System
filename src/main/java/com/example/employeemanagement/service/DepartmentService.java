package com.example.employeemanagement.service;

import com.example.employeemanagement.model.Department;
import com.example.employeemanagement.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public Department createDepartment(Department department) {
        return departmentRepository.save(department);
    }

    public Optional<Department> getDepartmentById(Long id) {
        return departmentRepository.findById(id);
    }

    public Department updateDepartment(Long id, Department departmentDetails) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));
        department.setName(departmentDetails.getName());
        department.setDepartmentHead(departmentDetails.getDepartmentHead());
        return departmentRepository.save(department);
    }

    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }

    public Page<Department> getAllDepartments( Pageable pageable) {
        return departmentRepository.findAll(pageable);
    }
}