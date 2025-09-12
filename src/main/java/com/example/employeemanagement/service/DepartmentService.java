package com.example.employeemanagement.service;

import com.example.employeemanagement.Entity.Department;
import com.example.employeemanagement.repository.DepartmentRepository;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public Department createDepartment(Department department) {
        return departmentRepository.save(department);
    }

//    public Optional<Department> getDepartmentById(UUID id) {
//        return departmentRepository.findById(id);
//    }

    public Department updateDepartment(UUID id, Department departmentDetails) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));
        department.setName(departmentDetails.getName());
        department.setDepartmentHead(departmentDetails.getDepartmentHead());
        return departmentRepository.save(department);
    }

    public void deleteDepartment(UUID id) {
        departmentRepository.deleteById(id);
    }

    public Page<Department> getAllDepartments( Pageable pageable) {
        return departmentRepository.findAll(pageable);
    }
}