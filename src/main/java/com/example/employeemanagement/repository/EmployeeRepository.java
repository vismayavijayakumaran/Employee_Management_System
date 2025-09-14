package com.example.employeemanagement.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.employeemanagement.Entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

    boolean existsByEmail(String email);

    boolean existsByDepartmentId(UUID id);
    
    List<Employee> findByDepartmentId(UUID departmentId);
}