package com.file.sorting.infrastructure.exception.handler;

import com.file.sorting.infrastructure.exception.handler.dto.ExceptionDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatusCode status, WebRequest request) {
        log.error(ex.getLocalizedMessage());
        Map<String, Object> body = prepareResponseMap(ex, status);
        return new ResponseEntity<>(body, headers, status);
    }

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

    private Map prepareResponseMap(Exception e, HttpStatusCode status) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());
        List<String> errors = new ArrayList<>();
        if (e instanceof MethodArgumentNotValidException)
            errors = ((MethodArgumentNotValidException) e).getBindingResult()
                    .getFieldErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
        else
            errors.add(e.getMessage());
        body.put("errors", errors);
        body.put("stackTrace", e.getStackTrace());
        return body;
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
