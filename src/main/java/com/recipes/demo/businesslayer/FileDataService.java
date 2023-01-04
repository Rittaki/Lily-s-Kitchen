package com.recipes.demo.businesslayer;

import com.recipes.demo.persistence.FileDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import lombok.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;
import java.util.UUID;

@Service
public class FileDataService {
    @Autowired
    private FileDataRepository repository;

    private final String FOLDER_PATH = System.getProperty("user.dir") + "\\uploads\\";
    public Optional<FileData> findFileByName(String name) {
        return repository.findByName(name);
    }
    public String uploadFile(MultipartFile file) throws IOException {
        if (repository.existsByName(file.getOriginalFilename())) {
            return file.getOriginalFilename();
        }
        String filePath = FOLDER_PATH + file.getOriginalFilename();
        FileData fileData = repository.save(FileData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .filePath(filePath).build());

        file.transferTo(new File(filePath));
        if (fileData != null) {
            return fileData.getName();
        }
        return null;
    }

    public byte[] downloadFile(String fileName) throws IOException {
        Optional<FileData> fileData = repository.findByName(fileName);
        String filePath = fileData.get().getFilePath();
        byte[] file = Files.readAllBytes(new File(filePath).toPath());
        return file;
    }
}
