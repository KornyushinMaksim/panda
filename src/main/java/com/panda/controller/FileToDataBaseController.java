package com.panda.controller;

import com.panda.dto.FileToDataBaseDto;
import com.panda.service.FileToDataBaseService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/files")
public class FileToDataBaseController {

    private final FileToDataBaseService fileToDataBaseService;

    @PostMapping("/add-file")
    public void addFile(@RequestParam String nameFile,
                        HttpServletResponse response) throws IOException {
        fileToDataBaseService.addNewFile(nameFile, response);
    }

    @PostMapping("/upload-file")
    public void uploadFile(@RequestParam("file") MultipartFile multipartFile,
                           @RequestParam("employeeId") UUID employeeId) {
        fileToDataBaseService.uploadFile(multipartFile, employeeId);
    }

    @GetMapping("/download-file/{id}")
    public void downloadFile(@PathVariable UUID id,
                             HttpServletResponse response
    ) {
        fileToDataBaseService.downloadFile(id, response);
    }

    @GetMapping("/get-file-by-id")
    public FileToDataBaseDto getFileById(@RequestParam UUID id) {
        return fileToDataBaseService.getFileById(id);
    }

    @GetMapping("/all-files")
    public List<FileToDataBaseDto> getAllFiles() {
        return fileToDataBaseService.getAllFiles();
    }

    @GetMapping("/properties-file")
    public FileToDataBaseDto propertiesFile(@RequestParam UUID id) {
        return fileToDataBaseService.getFileById(id);
    }
}
