package com.example.employeemanagement.exception;

public class DepartmentServiceException extends RuntimeException {
    public DepartmentServiceException(String message) {
        super(message);
    }
}