package com.file.sorting.service.file.impl;

import com.file.sorting.infrastructure.utils.FileUtils;
import com.file.sorting.model.Directory;
import com.file.sorting.service.file.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;

@Service
public class FileServicesImpl implements FileService {

    @Override
    public List<File> prepareStructure() {
        return List.of(FileUtils.createDirectory(Directory.HOME.toString()),
                FileUtils.createDirectory(Directory.DEV.toString()),
                FileUtils.createDirectory(Directory.TEST.toString()));
    }

    @Override
    public File saveFileToHome(MultipartFile file) throws IOException {
        return FileUtils.saveMultipartFile(file, MessageFormat.format("{0}/{1}", Directory.HOME, file.getOriginalFilename()));
    }

}
