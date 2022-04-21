package com.lrsrodrigues.spring_boot_aws_secret_manager;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/hello-world")
public class HelloWorldResource {

    @GetMapping
    public String helloWorld() {
        return "Hello Wolrd!";
    }
}
