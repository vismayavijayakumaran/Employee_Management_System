package com.example.employeemanagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    private String name;

    private LocalDateTime dateOfBirth;

    private Double salary;

    private String department;

    private String address;

    private String role;

    @Column(nullable = false)
    private LocalDateTime joiningDate;

    private Double yearlyBonusPercentage;

    private String reportingManager;
}