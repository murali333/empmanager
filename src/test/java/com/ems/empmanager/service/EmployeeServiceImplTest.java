package com.ems.empmanager.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;

import com.ems.empmanager.dto.EmployeeDto;
import com.ems.empmanager.exception.EmployeeFileNotFoundException;

class EmployeeServiceImplTest {

    @Test
    void parsesCsvSkippingHeader() {
        String csv = "emp_id,emp_name,emp_salary,emp_department\n101,John Doe,75000,Engineering\n";
        var service = new EmployeeServiceImpl(new ByteArrayResource(csv.getBytes(StandardCharsets.UTF_8)));
        List<EmployeeDto> result = service.getAllEmployees();
        assertEquals(List.of(new EmployeeDto(101, "John Doe", new BigDecimal("75000"), "Engineering")), result);
    }

    @Test
    void missingFileThrows() {
        var service = new EmployeeServiceImpl(new ClassPathResource("does-not-exist.csv"));
        assertThrows(EmployeeFileNotFoundException.class, service::getAllEmployees);
    }
}

