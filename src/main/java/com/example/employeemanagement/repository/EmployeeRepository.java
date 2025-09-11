package com.example.employeemanagement.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.employeemanagement.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
    // Additional query methods can be defined here if needed
}