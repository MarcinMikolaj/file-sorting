package com.file.sorting.infrastructure.exception.handler;

import com.file.sorting.infrastructure.exception.exceptions.IllegalFileExtensionException;
import com.file.sorting.infrastructure.exception.handler.dto.ExceptionDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.util.*;

@Slf4j
@RestControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<?> handleExceptions(Exception ex, WebRequest webRequest){
        log.error(ex.getLocalizedMessage());
        return new ResponseEntity<>(prepareExceptionDto(ex, HttpStatus.INTERNAL_SERVER_ERROR, Collections.singletonList(ex.getMessage()),
                webRequest.getDescription(false), ((ServletWebRequest) webRequest).getHttpMethod().toString(),ex.getClass().getName()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<?> handleIOException(Exception ex, WebRequest webRequest){
        log.error(ex.getLocalizedMessage());
        return new ResponseEntity<>(prepareExceptionDto(ex, HttpStatus.INTERNAL_SERVER_ERROR, Collections.singletonList(ex.getMessage()),
                webRequest.getDescription(false), ((ServletWebRequest) webRequest).getHttpMethod().toString(),ex.getClass().getName()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalFileExtensionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleIllegalFileExtensionException(Exception ex, WebRequest webRequest){
        log.error(ex.getLocalizedMessage());
        return new ResponseEntity<>(prepareExceptionDto(ex, HttpStatus.BAD_REQUEST, Collections.singletonList(ex.getMessage()),
                webRequest.getDescription(false), ((ServletWebRequest) webRequest).getHttpMethod().toString(),ex.getClass().getName()),
                HttpStatus.BAD_REQUEST);
    }

    public ExceptionDto prepareExceptionDto(Exception e, HttpStatus status, List<String> messages, String path,
                                            String method, String exception){
        return ExceptionDto.builder()
                .timestamp(new Date())
                .status(status.value())
                .messages(messages)
                .path(path)
                .method(method)
                .exception(exception)
                .build();
    }

}
