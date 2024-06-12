package com.panda.dto;

import com.panda.model.Customer;
import com.panda.model.Employee;
import com.panda.model.FileToDataBase;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileTaskDto {

    private UUID id;

    private FileToDataBaseDto fileId;

    private EmployeeDto authorTask;

    private CustomerDto customerId;

    private Set<EmployeeDto> executors;

    private LocalDate deadLine;

}
