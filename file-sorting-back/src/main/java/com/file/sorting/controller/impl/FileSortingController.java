package com.file.sorting.controller.impl;

import com.file.sorting.controller.FileSortingControllerApi;
import com.file.sorting.service.file.FileSortingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class FileSortingController implements FileSortingControllerApi  {
    private final FileSortingService fileSortingService;

    @Override
    public ResponseEntity<?> uploadFile(MultipartFile file) throws IOException {
        fileSortingService.createDirectoryStructure();
        fileSortingService.saveFileToFolder(file, "HOME/" + file.getOriginalFilename());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
