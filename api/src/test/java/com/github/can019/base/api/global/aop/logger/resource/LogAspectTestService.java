package com.github.can019.base.api.global.aop.logger.resource;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogAspectTestService {
    private final LogAspectTestRepository repository;

    void executeVoidReturnMethodInService(Object arg1, Object arg2) {
        repository.executeVoidReturnMethodInRepository(arg1, arg2);
    }
}
