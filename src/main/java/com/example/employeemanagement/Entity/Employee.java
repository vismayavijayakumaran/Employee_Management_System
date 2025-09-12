package com.example.employeemanagement.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import com.example.employeemanagement.DTO.EmployeeResponse;

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

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Date of birth is required")
    private LocalDate dateOfBirth;

    @NotNull(message = "Salary is required")
    private Double salary;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "Role is required")
    private String role;

    @NotNull(message = "Joining date is required")
    private LocalDate joiningDate;

    private Double yearlyBonusPercentage;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Employee reportingManager;

    @Column(nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT now()")
    private LocalDateTime createdAt = LocalDateTime.now();

    public EmployeeResponse toDto() {
        EmployeeResponse dto = new EmployeeResponse();
        dto.setId(this.getId());
        dto.setName(this.getName());
        dto.setDateOfBirth(this.getDateOfBirth());
        dto.setSalary(this.getSalary());
        dto.setAddress(this.getAddress());
        dto.setRole(this.getRole());
        dto.setJoiningDate(this.getJoiningDate());
        dto.setYearlyBonusPercentage(this.getYearlyBonusPercentage());
        if (this.getDepartment() != null) {
            dto.setDepartmentID(this.getDepartment().getId());
            dto.setDepartmenName(this.getDepartment().getName());
        }
        if (this.getReportingManager() != null) {
            dto.setReportingManagerName(this.getReportingManager().getName());
        }
        return dto;
    }

}