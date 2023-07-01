package com.file.sorting.controller.health;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = "/v1/api/health", produces = MediaType.APPLICATION_JSON_VALUE)
public interface HealthControllerApi {
    @GetMapping
    ResponseEntity<?> getHealth();
}
