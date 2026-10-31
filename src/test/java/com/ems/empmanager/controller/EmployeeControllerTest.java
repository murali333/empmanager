package com.ems.empmanager.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.ems.empmanager.dto.EmployeeDto;
import com.ems.empmanager.exception.EmployeeFileNotFoundException;
import com.ems.empmanager.service.EmployeeService;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    EmployeeService employeeService;

    @Test
    void returnsEmployees() throws Exception {
        when(employeeService.getAllEmployees())
                .thenReturn(List.of(new EmployeeDto(101, "John Doe", new BigDecimal("75000"), "Engineering")));
        mockMvc.perform(get("/api/v1/empmanager/list-all-employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].empId").value(101))
                .andExpect(jsonPath("$[0].empName").value("John Doe"));
    }

    @Test
    void returns401WhenFileMissing() throws Exception {
        when(employeeService.getAllEmployees()).thenThrow(new EmployeeFileNotFoundException("employee.csv file not found"));
        mockMvc.perform(get("/api/v1/empmanager/list-all-employees"))
                .andExpect(status().isUnauthorized());
    }
}

