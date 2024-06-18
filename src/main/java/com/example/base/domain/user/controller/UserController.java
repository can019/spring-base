package com.example.base.domain.user.controller;

import com.example.base.domain.user.domain.User;
import com.example.base.domain.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/user")
    public ResponseEntity createUser(){
        User user = new User();
        String userId =  this.userService.createUser(user);

        Map<String, String> body = new HashMap<>();
        body.put("id", userId);

        return new ResponseEntity(body, HttpStatus.CREATED);
    }

    @GetMapping("/user")
    public ResponseEntity findOneById(@RequestParam String id){
        User user = this.userService.findOneById(id);
        Map<String, String> body = new HashMap<>();
        body.put("id", user.getIdAsString());

        return new ResponseEntity(body, HttpStatus.OK);
    }
}
