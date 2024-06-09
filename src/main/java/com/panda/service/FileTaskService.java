package com.panda.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.panda.dto.CustomerDto;
import com.panda.dto.EmployeeDto;
import com.panda.dto.FileTaskDto;
import com.panda.dto.FileToDataBaseDto;
import com.panda.mapper.EmployeeMapper;
import com.panda.mapper.FileTaskMapper;
import com.panda.model.Customer;
import com.panda.model.Employee;
import com.panda.model.FileTask;
import com.panda.model.FileToDataBase;
import com.panda.repository.FileTaskRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FileTaskService {

    private final FileTaskRepository fileTaskRepository;
    private final FileTaskMapper fileTaskMapper;
    private final EmployeeMapper employeeMapper;
    private final FileToDataBaseService fileToDataBaseService;
    private final CustomerService customerService;
    private final EmployeeService employeeService;

//    /**
//     * создание новой задачи
//     * @param fileTaskDto
//     * @return
//     */
//    @Transactional
//    public FileTaskDto addFileTask(Map<String, Object> fileTask) {
//
//        Map<String, Object> executors = (Map<String, Object>) fileTask.get("executors");
//        Set<Employee> employees = new HashSet<>();
//
//        for(Map.Entry entry: executors.entrySet()) {
//            UUID tmp = UUID.fromString((String) entry.getValue());
//            Employee employeeTmp = employeeMapper.toEntity
//                    (employeeService.getEmployeeById
//                            (tmp));
//
//            employees.add(employeeTmp);
//        }
//
//        UUID fileId = UUID.fromString((String) fileTask.get("fileId"));
//        FileToDataBase fileToDataBase = fileToDataBaseService.getFileById(fileId);
//
//        UUID customerId = UUID.fromString((String) fileTask.get("customerId"));
//        Customer customer = customerService.getCustomerById(customerId);
//
//        LocalDate localDate = LocalDate.parse((String) fileTask.get("deadLine"));
//
//        FileTask fileTaskSaved = FileTask.builder()
//                .fileId(fileToDataBase)
//                .customerId(customer)
//                .deadLine(localDate)
//                .executors(employees)
//                .build();
//
//        fileTaskRepository.save(fileTaskSaved);
//
//        FileTaskDto fileTaskDto = fileTaskMapper.toDto(fileTaskSaved);
//        fileTaskDto.setExecutors(employees);
//
//        return fileTaskDto;
//    }

    public FileTaskDto addFileTask(FileTaskDto fileTaskDto) {

        List<UUID> employeeIds = fileTaskDto.getExecutors().stream()
                .map(EmployeeDto::getId).toList();

        Set<Employee> employees = employeeService.getEmployees(employeeIds);

        FileTask entity = fileTaskMapper.toEntity(fileTaskDto);
        entity.setExecutors(employees);

        FileTask fileTaskSaved = fileTaskRepository.save(entity);

        FileTaskDto dto = fileTaskMapper.toDto(fileTaskSaved);
        dto.setExecutors(employees.stream()
                .map(employeeMapper::toDto)
                .collect(Collectors.toSet()));

        return dto;
    }

    /**
     * изменение задачи
     * @param fileTaskDto
     * @return
     */
    @Transactional
    public FileTaskDto updateFileTask(FileTaskDto fileTaskDto) {

        FileTask fileTaskUpdate = fileTaskRepository.findById(fileTaskDto.getId()).orElseThrow();
        FileTask fileTaskSaved = fileTaskMapper.marge(fileTaskDto, fileTaskUpdate);

        fileTaskRepository.save(fileTaskSaved);

        return fileTaskMapper.toDto(fileTaskSaved);
    }
}
