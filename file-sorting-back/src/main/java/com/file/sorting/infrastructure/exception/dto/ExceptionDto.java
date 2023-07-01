package com.file.sorting.infrastructure.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class ExceptionDto {
    private Date timestamp;
    private int status;
    private String path;
    private String method;
    private String exception;
    private List<String> messages;
}
