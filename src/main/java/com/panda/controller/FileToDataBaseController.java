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
@RequestMapping("/files")
public class FileToDataBaseController {

    private final FileToDataBaseService fileToDataBaseService;

    @PostMapping("/add-file")
    public void addFile(@RequestParam String nameFile,
                        HttpServletResponse response) throws IOException {
        fileToDataBaseService.addNewFile(nameFile, response);
    }

    @PostMapping("/upload-file")
    public void uploadFile(@RequestParam("file") MultipartFile multipartFile,
                           @RequestParam UUID employeeId) {
        fileToDataBaseService.uploadFile(multipartFile, employeeId);
    }

//    @PostMapping("/update-file")
//    public byte[] updateFile(@RequestParam("fileId") UUID fileId,   //????
//                             @RequestParam("employeeId") UUID employeeId) {
//        return fileToDataBaseService.updateFile(fileId, employeeId);
//    }

    @GetMapping("/download-file")
    public void downloadFile(@RequestParam UUID id
                                , HttpServletResponse response
    ) {   //!!!
        fileToDataBaseService.downloadFile(id, response);
    }

    @GetMapping("/file-info")
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
