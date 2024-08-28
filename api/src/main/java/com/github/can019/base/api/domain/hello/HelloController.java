package com.github.can019.base.api.domain.hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Simple hello controller
 *
 * @since 0.1.5
 */
@RestController
public class HelloController {

    @GetMapping("/api/v0/hello")
    public String hello() {
        return "hello";
    }
}
