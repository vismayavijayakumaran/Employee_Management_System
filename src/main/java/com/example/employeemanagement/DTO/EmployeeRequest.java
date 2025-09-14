package com.example.employeemanagement.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequest {

    private String firstname;

    private String lastname;

    private LocalDate dateOfBirth;

    private Double salary;

    private String address;

    private String role;

    private LocalDate joiningDate;

    private Double yearlyBonusPercentage;

    private UUID departmentID;

    private UUID reportingManagerId;
}
