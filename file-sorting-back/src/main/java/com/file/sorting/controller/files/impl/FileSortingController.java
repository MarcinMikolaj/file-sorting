package com.file.sorting.controller.files.impl;

import com.file.sorting.controller.files.FileSortingControllerApi;
import com.file.sorting.service.file.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class FileSortingController implements FileSortingControllerApi  {
    private final FileService fileService;

    @Override
    public ResponseEntity<?> uploadFile(MultipartFile file) throws IOException {
        fileService.prepareStructure();
        return new ResponseEntity<>(fileService.saveFileToHome(file).getAbsoluteFile().getName(), HttpStatus.OK);
    }
}
