package com.panda.service;

import com.panda.dto.FileToDataBaseDto;
import com.panda.mapper.EmployeeMapper;import com.panda.mapper.FileToDataBaseMapper;
import com.panda.model.FileTask;
import com.panda.model.FileToDataBase;
import com.panda.repository.FileToDataBaseRepository;
import com.panda.mapper.EmployeeMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FileToDataBaseService {

    private final FileToDataBaseRepository fileToDataBaseRepository;
    private final EmployeeService employeeService;
    private final FileToDataBaseMapper fileToDataBaseMapper;
    private final EmployeeMapper employeeMapper;
    @Value("${upload.path}")
    private String pathToStorage;

    /**
     * создание файла
     * создает файл и добавляет информацию в БД
     * @param nameFile
     */
    @Transactional
    public void addNewFile(String nameFile) {

        try {
            File file = new File(pathToStorage + "/" + nameFile);
            if (file.createNewFile()) {

                FileToDataBase fileToDataBase = FileToDataBase.builder()
                        .nameFile(file.getName())
                        .typeFile(formatFile(file.getName(), "."))
                        .dateOfChange(LocalDate.now())
                        .size(file.length())
                        .pathToStorage(file.getPath())
                        .isActive(false)
                        .build();

                fileToDataBaseRepository.save(fileToDataBase);
                System.out.println("Файл создан"); //заменить на логи

            } else {
                System.out.println("Файл уже существует"); //заменить на логи
            }

        } catch (IOException e) {
            System.out.println("Ошибка при создании файла"); //заменить на логи
            e.printStackTrace();
        }
    }

//    @Transactional
//    public void openFileById(UUID id) {
//
//        String path = getById(id).getPathToStorage();
//    }

    /**
     * скачивание файлов из хранилища на комп
     * принимает id, по которому получает путь хранения нужного файла
     * кодирует файл и в виде массива байт передает на фронт
     *
     * @param id
     */
    @Transactional
    public byte[] downloadFile(UUID id) {

        String filePath = getFileById(id).getPathToStorage();
        System.out.println(filePath);
        byte[] encodedFile = null;

        try {
            encodedFile = Base64.getEncoder()
                    .encode(Files.readAllBytes(Paths.get(filePath)));
//            Files.write(Paths.get(nameFile), Base64.getDecoder().decode(encodedFile));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return encodedFile;
    }

    /**
     * возвращает данные об объекте из БД по id
     * @param id
     * @return
     */
    public FileToDataBaseDto getFileById(UUID id) {

        FileToDataBase fileToDataBase = fileToDataBaseRepository.findById(id).orElseThrow();

        return fileToDataBaseMapper.toDto(fileToDataBase);
    }

    /**
     * получение данных из таблицы по имени файла
     * @param nameFile
     * @return
     */
    public FileToDataBase getFileToDataBaseByName (String nameFile) {

        return fileToDataBaseRepository.findByNameFile(nameFile);
    }

    /**
     * загрузка файла в хранилище
     * принимает файл в формате multipartFile
     * создает файл, если такого нет
     * записывает в него содержимое
     * записывает информацию о файле в БД
     * ставит метку isActive=false
     * колонка id_employee принимает значение null
     * @param multipartFile
     */
    public void uploadFile(MultipartFile multipartFile, UUID employeeId) {

        String path = pathToStorage +"/" + multipartFile.getOriginalFilename();
        String nameMultiPartFile = multipartFile.getOriginalFilename();
        FileToDataBase fileToDB = getFileToDataBaseByName(nameMultiPartFile);

        if (nameMultiPartFile != null) {

            try {
                File file = new File(path);

                if (file.createNewFile()) {

                    FileToDataBase fileToDataBase = FileToDataBase.builder()
                            .pathToStorage(path)
                            .nameFile(nameMultiPartFile)
                            .typeFile(formatFile(nameMultiPartFile, "."))
                            .dateOfChange(LocalDate.now())
                            .size(multipartFile.getSize())
                            .isActive(false)
                            .employee(null)
                            .build();


                    writeToFile(path, multipartFile);
                    fileToDataBaseRepository.save(fileToDataBase);
                    System.out.println("Файл создан"); //заменить на логи, выводить сообщение на фронт

                } else {
                    if (fileToDB.getIsActive() && fileToDB.getEmployee().getId().equals(employeeId)) {

                        writeToFile(path, multipartFile);
                        fileToDB.setDateOfChange(LocalDate.now());
                        fileToDB.setSize(multipartFile.getSize());
                        fileToDB.setIsActive(false);
                        fileToDB.setEmployee(null);
                        fileToDataBaseRepository.save(fileToDB);
                    } else {
                        //логика с подтверждением презаписи
                    }
// прописать условия
                    System.out.println("Файл уже существует"); //заменить на логи, выводить сообщение на фронт

                }

            } catch (IOException e) {
                System.out.println("Ошибка при создании файла"); //заменить на логи, выводить сообщение на фронт
                e.printStackTrace();
            }
        }
    }

    /**
     * возвращает файл в виде массива байт
     * ставит метку isActive=true
     * записывает id сотрудника, который взял в работу файл
     * @param fileId
     * @param employeeId
     * @return
     */
    public byte[] updateFile(UUID fileId, UUID employeeId) {

        FileToDataBaseDto fileToDataBaseDto = getFileById(fileId);
        FileToDataBase fileToDataBase = fileToDataBaseMapper.toEntity(fileToDataBaseDto);

        fileToDataBase.setIsActive(true);
        fileToDataBase.setEmployee(employeeMapper.toEntity(employeeService.getEmployeeById(employeeId)));

        fileToDataBaseRepository.save(fileToDataBase);

//        getFileById(fileId)
//                .setIsActive(true);
//        getFileById(fileId)
//                .setEmployee(employeeMapper.toEntity(employeeService.getEmployeeById(employeeId)));
        return downloadFile(fileId);
    }

    /**
     * получение строки по разделителю
     * на вход принимает строку и символ по которому разделить
     * используется для:
     * 1. получения формата файла для таблицы file_entity
     * 2. получения имени и формата файла при его копировании
     *
     * @param nameFile
     * @param delimiter
     * @return
     */
    private String formatFile(String nameFile, String delimiter) {

        int index = nameFile.lastIndexOf(delimiter);

        return nameFile.substring(index + 1);
    }

    /**
     * записывает файл
     * @param path
     * @param multipartFile
     */
    private void writeToFile (String path, MultipartFile multipartFile) {

        try (FileOutputStream fos = new FileOutputStream(path)) {
            byte[] contentFile = multipartFile.getBytes();
            fos.write(contentFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * возвращает список файлов
     * @return
     */
    public List<FileToDataBaseDto> getAllFiles() {

        List<FileToDataBaseDto> filesDto = fileToDataBaseRepository.findAll().stream()
                .map(fileToDataBaseMapper::toDto)
                .collect(Collectors.toList());

        return filesDto;
    }
}
