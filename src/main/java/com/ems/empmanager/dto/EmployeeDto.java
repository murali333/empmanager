package com.ems.empmanager.dto;

import java.math.BigDecimal;

public record EmployeeDto(int empId, String empName, BigDecimal empSalary, String empDepartment) {
}

