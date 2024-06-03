package com.panda.controller;

import com.panda.service.FileToDataBaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/files")
public class FileToDataBaseController {

    private final FileToDataBaseService fileToDataBaseService;

    @PostMapping("/add-file{nameFile}")
    public void addFile(@RequestParam String nameFile) throws IOException {
        fileToDataBaseService.addNewFile(nameFile);
    }

    @GetMapping("/download-file")
    public byte[] downloadFile(@RequestParam UUID id) {
        return fileToDataBaseService.downloadFile(id);
    }

    @PostMapping("/upload-file")
    public void uploadFile(@RequestParam("file") MultipartFile multipartFile,
                           @RequestParam UUID employeeId) {
        fileToDataBaseService.uploadFile(multipartFile, employeeId);
    }

    @PostMapping("/update-file")
    public byte[] updateFile(@RequestParam("fileId") UUID fileId,
                             @RequestParam("employeeId") UUID employeeId) {
        return fileToDataBaseService.updateFile(fileId, employeeId);
    }
}
