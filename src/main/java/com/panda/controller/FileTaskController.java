package com.panda.controller;

import com.panda.dto.FileTaskDto;
import com.panda.service.FileTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class FileTaskController {

    private final FileTaskService fileTaskService;

    @PostMapping("/add-task")
    public FileTaskDto addFileTask(@RequestBody FileTaskDto fileTaskDto) {

        System.out.println(fileTaskDto.getFileId());
        return fileTaskService.addFileTask(fileTaskDto);

        //нужно продумать как принять несколько параметров и метод addFileTask прописать вручную, без mapper
    }
}
