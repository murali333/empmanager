package com.ems.empmanager.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.ems.empmanager.exception.InvalidBranchException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.ems.empmanager.dto.EmployeeDto;
import com.ems.empmanager.exception.EmployeeFileNotFoundException;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final String FILE_NAME = "employee.csv";

    private final Resource resource;

    public EmployeeServiceImpl() {
        this(new ClassPathResource(FILE_NAME));
    }

    EmployeeServiceImpl(Resource resource) {
        this.resource = resource;
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        if (!resource.exists()) {
            throw new EmployeeFileNotFoundException(FILE_NAME + " file not found");
        }
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
            return reader.lines()
                    .skip(1)
                    .filter(line -> !line.isBlank())
                    .map(this::toDto)
                    .toList();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private EmployeeDto toDto(String line) {
        String[] f = line.split(",", -1);
        return new EmployeeDto(Integer.parseInt(f[0].trim()), f[1].trim(),
                new BigDecimal(f[2].trim()), f[3].trim());
    }

    @Override
    public void validateBranch(String branch) {
        if (!branch.matches("^[A-Z]{3}$")) {
            throw new InvalidBranchException("Invalid branch: " + branch);
        }
    }
}

