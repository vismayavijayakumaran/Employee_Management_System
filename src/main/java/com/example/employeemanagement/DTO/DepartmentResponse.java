package com.example.employeemanagement.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentResponse {

    private UUID id;

    private String name;

    private LocalDateTime createdAt;

    private String departmentHeadName;
}
