package com.file.sorting.controller.files.impl;

import com.file.sorting.controller.files.FileSortingControllerApi;
import com.file.sorting.infrastructure.utils.FileUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class FileSortingController implements FileSortingControllerApi  {

    @Override
    public ResponseEntity<?> uploadFile(MultipartFile file) throws IOException {
        FileUtils.createDirectoryStructure();
        FileUtils.saveFileToFolder(file, "HOME/" + file.getOriginalFilename());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
