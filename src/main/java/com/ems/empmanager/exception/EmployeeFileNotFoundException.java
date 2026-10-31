package com.ems.empmanager.exception;

public class EmployeeFileNotFoundException extends RuntimeException {
    public EmployeeFileNotFoundException(String message) {
        super(message);
    }
}
