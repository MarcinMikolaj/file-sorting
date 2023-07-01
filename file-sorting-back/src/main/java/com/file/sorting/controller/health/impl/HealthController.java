package com.file.sorting.controller.health.impl;

import com.file.sorting.controller.health.HealthControllerApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController implements HealthControllerApi {
    @Override
    public ResponseEntity<?> getHealth() {
        return new ResponseEntity<>("UP", HttpStatus.OK);
    }
}
