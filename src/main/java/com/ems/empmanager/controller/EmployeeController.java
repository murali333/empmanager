package com.ems.empmanager.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ems.empmanager.dto.EmployeeDto;
import com.ems.empmanager.service.EmployeeService;

@RestController
@RequestMapping("/api/v1/empmanager")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/list-all-employees")
    public List<EmployeeDto> listAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/validate-name")
    public Map<String, Object> validateName(@RequestParam String name) {
        employeeService.validateBranch(name);
        return Map.of("name", name, "valid", true);
    }
}
