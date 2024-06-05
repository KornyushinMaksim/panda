package com.panda.service;

import com.panda.dto.FileTaskDto;
import com.panda.mapper.FileTaskMapper;
import com.panda.model.FileTask;
import com.panda.repository.FileTaskRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileTaskService {

    private final FileTaskRepository fileTaskRepository;
    private final FileTaskMapper fileTaskMapper;

    /**
     * создание новой задачи
     * @param fileTaskDto
     * @return
     */
    @Transactional
    public FileTaskDto addFileTask(FileTaskDto fileTaskDto) {

        FileTask fileTaskSaved = fileTaskMapper.toEntity(fileTaskDto);
        fileTaskRepository.save(fileTaskSaved);

        return fileTaskMapper.toDto(fileTaskSaved);
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
