package com.github.can019.base.core.handler;

import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Profile("test")
public class GlobalExceptionHandlerSliceTestController {
    public static final String GET_RESPONSE_BODY = "test";
    public static final String GET_MAPPING_URI = "/simple-test-controller/get";
    public static final String WRONG_METHOD_URI_WHEN_USE_GET = "/simple-test-controller/wrong-method-use-get";
    @GetMapping(GET_MAPPING_URI)
    public ResponseEntity get(){
        return ResponseEntity.status(HttpStatus.OK).body(GET_RESPONSE_BODY);
    }

    @PostMapping(WRONG_METHOD_URI_WHEN_USE_GET)
    public ResponseEntity wrongMethodURI_WHEN_USE_GET(){
        return ResponseEntity.status(HttpStatus.OK).body(GET_RESPONSE_BODY);
    }
}
