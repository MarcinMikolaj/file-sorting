package com.file.sorting.service.file;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface FileService {
    List<File> prepareStructure();
    File saveFileToHome(MultipartFile file) throws IOException;
}
