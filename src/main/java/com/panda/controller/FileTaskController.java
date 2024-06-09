package com.panda.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.panda.dto.FileTaskDto;
import com.panda.model.Employee;
import com.panda.service.EmployeeService;
import com.panda.service.FileTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.*;
import java.lang.reflect.Array;
import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class FileTaskController {

    private final FileTaskService fileTaskService;
    private final EmployeeService employeeService;

    @PostMapping("/add-task")
    public FileTaskDto addFileTask(@RequestBody FileTaskDto fileTaskDto) {

        return fileTaskService.addFileTask(fileTaskDto);

        //в пункт executors кладем массив с сотрудниками, которые ртветственны за работу
        //по данному документу

        //нужно продумать как принять несколько параметров и метод addFileTask прописать вручную, без mapper
    }
}
