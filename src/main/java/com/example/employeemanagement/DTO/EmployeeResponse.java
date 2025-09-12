package com.example.employeemanagement.DTO;

import com.example.employeemanagement.Entity.Department;
import com.example.employeemanagement.Entity.Employee;

import java.time.LocalDate;
import java.util.UUID;

public class EmployeeResponse {

    private UUID id;

    private String name;

    private LocalDate dateOfBirth;

    private Double salary;

    private String address;

    private String role;

    private LocalDate joiningDate;

    private Double yearlyBonusPercentage;

    private String department;

    private String reportingManager;
}
