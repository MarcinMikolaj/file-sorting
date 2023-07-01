package com.file.sorting.service.file.impl;

import com.file.sorting.service.file.FileSortingService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class FileSortingServiceImpl implements FileSortingService {

    @Override
    public String saveFileToFolder(MultipartFile file, String path) throws IOException {
        File file1 = new File(path);
        file.transferTo(file1.getAbsoluteFile());
        return path;
    }

    public void createDirectoryStructure() {
        new File("HOME").mkdirs();
        new File("DEV").mkdirs();
        new File("TEST").mkdirs();
    }
}
