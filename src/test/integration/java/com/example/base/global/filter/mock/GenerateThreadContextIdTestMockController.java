package com.example.base.global.filter.mock;


import org.apache.logging.log4j.ThreadContext;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Profile("GENERATE_THREAD_CONTEXT_ID_TEST")
// 또는 @Conditional 사용
@RestController
public class GenerateThreadContextIdTestMockController {

    @GetMapping("/foo")
    public ResponseEntity foo() {
        return ResponseEntity.status(HttpStatus.OK).body(ThreadContext.get("id").toString());
    }
}
