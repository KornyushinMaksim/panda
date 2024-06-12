package com.panda.controller;

import com.panda.dto.FileTaskDto;
import com.panda.service.FileTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class FileTaskController {

    private final FileTaskService fileTaskService;

    @PostMapping("/add-task")
    public FileTaskDto addFileTask(@RequestBody FileTaskDto fileTaskDto) {

        return fileTaskService.addFileTask(fileTaskDto);
    }

    @GetMapping("/all-tasks")
    public List<FileTaskDto> getAllTasks() {
        return fileTaskService.getAllTasks();
    }
}
