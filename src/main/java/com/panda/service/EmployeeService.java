package com.panda.service;

import com.panda.dto.EmployeeDto;
import com.panda.dto.EmployeeDto;
import com.panda.mapper.EmployeeMapper;
import com.panda.model.Employee;
import com.panda.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    /**
     * добавление сотрудника
     * @param employee
     */
    @Transactional
    public void addEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    /**
     * получение списка сотрудников
     * @return
     */
    public List<EmployeeDto> getAllEmployees() {

        return employeeRepository.findAll().stream()
                .map(employeeMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * получение сотрудника по id
     * @param id
     * @return
     */
    public EmployeeDto getEmployeeById(UUID id) {

        Employee employee = employeeRepository.findById(id).orElseThrow();

        return employeeMapper.toDto(employee);
    }

    /**
     * редактирование сотрудника
     * @param employeeDto
     * @return
     */
    @Transactional
    public EmployeeDto updateEmployee(EmployeeDto employeeDto) {

        Employee employee = employeeRepository.findById(employeeDto.getId()).orElseThrow();
        Employee employeeSaved = employeeRepository.save(employeeMapper.merge(employeeDto, employee));

        return employeeMapper.toDto(employeeSaved);
    }

    /**
     * удаление сотрудника по id
     * @param id
     */
    @Transactional
    public void deleteEmployeeById(UUID id) {
        employeeRepository.delete(employeeMapper.toEntity(getEmployeeById(id)));
    }

    public Optional<Employee> findEmployeeByAuthenticationLogin(String username) {
        return employeeRepository.findEmployeeByAuthenticationLogin(username);
    }

//    public Optional<Employee> findEmployeeByAuthenticationUsername(String username) {
//        return employeeRepository.findEmployeeByAuthenticationLogin(username);
//    }
}
