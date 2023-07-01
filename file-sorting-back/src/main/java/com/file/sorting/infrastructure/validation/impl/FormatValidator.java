package com.file.sorting.infrastructure.validation.impl;

import com.file.sorting.infrastructure.utils.FileUtils;
import com.file.sorting.infrastructure.validation.Format;
import com.file.sorting.model.Extension;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.stream.Stream;

public class FormatValidator implements ConstraintValidator<Format, MultipartFile> {

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext constraintValidatorContext) {
        return Stream.of(Extension.values())
                .anyMatch(e -> e.toString().equals(FileUtils.getFileExtension(Objects.requireNonNull(multipartFile.getOriginalFilename()))));

    }

}
