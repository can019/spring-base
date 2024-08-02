package com.github.can019.domain.hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Simple hello controller
 *
 * @Since 0.1.5
 */
@RestController
public class HelloController {

    @GetMapping("/api/v0/hello")
    public String hello() {
        return "hello";
    }
}
