package com.example.base.global.devkit.exception.service;

import com.example.base.global.devkit.exception.repository.DevExceptionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("test | local | local_docker | dev")
@Service
@Transactional
public class DevExceptionService {
    private final DevExceptionRepository devExceptionRepository;

    @Autowired
    public DevExceptionService(DevExceptionRepository devExceptionRepository){
        this.devExceptionRepository = devExceptionRepository;
    }

    public void exceptionOnService(String arg) {
        throw new RuntimeException("on service");
    }

    public void exceptionOnRepository(String arg){
        this.devExceptionRepository.exceptionOnRepository(arg);
    }

    public void exceptionOnJpa(String arg) {
        this.devExceptionRepository.exceptionOnJpa(arg);
    }
}
