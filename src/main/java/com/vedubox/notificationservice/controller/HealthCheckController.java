package com.vedubox.notificationservice.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/public/api/health-check")
@RequiredArgsConstructor
public class HealthCheckController {

    private final Logger log;

    @Value("${spring.application.name:Application name not defined.}")
    private String springApplicationName;


    @GetMapping(path = "/error", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    public ResponseEntity error() {
        log.error("HealthCheckController.error");
        return ResponseEntity.ok(springApplicationName);
    }

    @GetMapping(path = "/warn", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    public ResponseEntity warn() {
        log.warn("HealthCheckController.warn");
        return ResponseEntity.ok(springApplicationName);
    }

    @PostMapping(path = "/info", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    public ResponseEntity info() {
        log.warn("HealthCheckController.info");
        return ResponseEntity.ok(springApplicationName);
    }
}

