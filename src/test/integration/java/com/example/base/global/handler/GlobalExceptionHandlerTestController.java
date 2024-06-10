package com.example.base.global.handler;

import com.example.base.global.exception.ApplicationException;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Profile("test")
public class GlobalExceptionHandlerTestController {
    public static final String GET_RESPONSE_BODY = "test";
    public static final String GET_MAPPING_URI = "/simple-test-controller/get";
    public static final String WRONG_METHOD_URI_WHEN_USE_GET = "/simple-test-controller/wrong-method-use-get";
    public static final String APPLICATION_EXCEPTION_URI = "/simple-test-controller/application-exception";

    @GetMapping(GET_MAPPING_URI)
    public ResponseEntity get(){
        return ResponseEntity.status(HttpStatus.OK).body(GET_RESPONSE_BODY);
    }

    @PostMapping(WRONG_METHOD_URI_WHEN_USE_GET)
    public ResponseEntity wrongMethodURI_WHEN_USE_GET(){
        return ResponseEntity.status(HttpStatus.OK).body(GET_RESPONSE_BODY);
    }

    @GetMapping(APPLICATION_EXCEPTION_URI)
    public ResponseEntity applicationException() throws ApplicationException {
        throw new ApplicationException("message");
    }
}
