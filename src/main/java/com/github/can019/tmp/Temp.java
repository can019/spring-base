package com.github.can019.tmp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class Temp {

    @GetMapping("/tmp")
    public String hi(){
        return "hi";
    }
}
