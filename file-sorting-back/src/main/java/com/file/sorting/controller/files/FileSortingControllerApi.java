package com.file.sorting.controller.files;

import com.file.sorting.infrastructure.validation.Format;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Validated
@RequestMapping(path = "/v1/api/file", produces = MediaType.APPLICATION_JSON_VALUE)
public interface FileSortingControllerApi {
    @PostMapping("/upload")
    ResponseEntity<?> uploadFile(@RequestParam(name = "file") @Format MultipartFile file) throws IOException;

}
