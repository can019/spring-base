package com.github.can019.domain.hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/api/v0/hello")
    public String hello() {
        return "hello";
    }
}
