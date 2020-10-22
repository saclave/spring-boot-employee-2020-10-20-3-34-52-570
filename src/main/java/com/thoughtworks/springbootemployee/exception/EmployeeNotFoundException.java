package com.thoughtworks.springbootemployee.exception;

public class EmployeeNotFoundException extends RuntimeException {
    public static final String EMPLOYEE_NOT_FOUND = "Employee Not Found";
    public EmployeeNotFoundException(String message) {
        super(message);
    }
}
