package com.github.can019.base.core.spring.boot.web.interceptor.resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoggingInterceptorTestController {

    @GetMapping("test/logging-interceptor/get")
    public ResponseEntity getMappingMethod() {
        return new ResponseEntity(null, HttpStatus.OK);
    }
}
