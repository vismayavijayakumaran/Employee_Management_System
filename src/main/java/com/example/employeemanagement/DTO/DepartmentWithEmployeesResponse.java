package com.example.employeemanagement.DTO;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentWithEmployeesResponse {
    private UUID id;
    private String name;
    private List<EmployeeResponse> employees;
}
