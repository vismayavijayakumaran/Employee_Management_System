package com.example.employeemanagement.DTO;

import java.util.UUID;
import lombok.Data;

@Data
public class EmployeeLookupResponse {
    private UUID id;
    private String name;
}
