package com.file.sorting.service.file;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileSortingService {
    String saveFileToFolder(MultipartFile file, String path) throws IOException;
    void createDirectoryStructure();
}
