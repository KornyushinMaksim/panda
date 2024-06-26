package com.panda.controller;

import com.panda.dto.EmployeeDto;
import com.panda.dto.EmployeeDto;
import com.panda.model.Department;
import com.panda.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.DeclareWarning;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {

    public final EmployeeService employeeService;

    @GetMapping("/get-all-employees")
    public List<EmployeeDto> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/get-employee-by-id{id}")
    public EmployeeDto getEmployeeById(@RequestParam UUID id) {
        return employeeService.getEmployeeById(id);
    }

    @PutMapping("/update-employee")
    public EmployeeDto updateEmployee(@RequestBody EmployeeDto employeeDto) {
//        System.out.println(new Department().getEmployees());
        return employeeService.updateEmployee(employeeDto);
    }
}

