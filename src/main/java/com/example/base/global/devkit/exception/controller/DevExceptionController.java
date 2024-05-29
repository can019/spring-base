package com.example.base.global.devkit.exception.controller;

import com.example.base.domain.user.repository.UserRepository;
import com.example.base.global.devkit.exception.service.DevExceptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Profile("test | local | local_docker | dev")
@Controller
@RequestMapping("/dev-kit/exception")
public class DevExceptionController {

    private final DevExceptionService devExceptionService;

    @Autowired
    public DevExceptionController(DevExceptionService devExceptionService){
        this.devExceptionService = devExceptionService;
    }

    @GetMapping("/on-controller")
    public ResponseEntity exceptionOnController(){
        throw new RuntimeException("test");
    }

    @GetMapping("/on-service")
    public ResponseEntity exceptionOnService(){
        this.devExceptionService.exceptionOnService("on-service");
        return new ResponseEntity("message from controller", HttpStatus.OK);
    }

    @GetMapping("/on-repository")
    public ResponseEntity exceptionOnRepository(){
        this.devExceptionService.exceptionOnRepository("on-repository");
        return new ResponseEntity("message from controller", HttpStatus.OK);
    }

    @GetMapping("/on-jpa")
    public ResponseEntity exceptionOnJpa(){
        this.devExceptionService.exceptionOnJpa("on-jpa");
        return new ResponseEntity("message from controller", HttpStatus.OK);
    }
}
