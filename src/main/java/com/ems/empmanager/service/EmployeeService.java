package com.ems.empmanager.service;

import java.util.List;

import com.ems.empmanager.dto.EmployeeDto;

public interface EmployeeService {
    List<EmployeeDto> getAllEmployees();

    void validateBranch(String branch);
}
