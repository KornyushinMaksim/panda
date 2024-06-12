package com.panda.dto;

import com.panda.model.Employee;
import com.panda.model.FileTask;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    private UUID id;

    private FileTaskDto fileTaskId;

    private EmployeeDto authorComment;

    private String comment;

    private LocalDateTime dateComment;
}
