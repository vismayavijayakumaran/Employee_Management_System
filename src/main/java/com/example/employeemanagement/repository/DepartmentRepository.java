package com.example.employeemanagement.repository;

import com.example.employeemanagement.Entity.Department;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, UUID> {
    boolean existsByName(String name);
}