package com.github.can019.base.api.global.aop.logger.resource;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LogAspectTestController {
    private final LogAspectTestService service;

    @GetMapping("test/log/void")
    public void executeVoidReturnMethodInController(Object arg1, Object arg2) {
        service.executeVoidReturnMethodInService(arg1, arg2);
    }
}
