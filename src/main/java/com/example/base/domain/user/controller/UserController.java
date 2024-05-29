package com.example.base.domain.user.controller;

import com.example.base.domain.user.domain.User;
import com.example.base.domain.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/user")
    public ResponseEntity createUser(){
        String userId =  this.userService.createUser(new User()).toString();
        return new ResponseEntity(userId, HttpStatus.CREATED);
    }
}
