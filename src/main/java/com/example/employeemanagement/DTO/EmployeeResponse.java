package com.example.employeemanagement.DTO;

import java.time.LocalDate;
import java.util.UUID;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponse {

    private UUID id;

    private String name;

    private LocalDate dateOfBirth;

    private Double salary;

    private String address;

    private String role;

    private LocalDate joiningDate;

    private Double yearlyBonusPercentage;

    private UUID departmentID;

    private String departmenName;

    private String reportingManagerName;
}
