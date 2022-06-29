package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {
    @GetMapping
    public String testController() {
        return "hello world";
    }

    @GetMapping("testGetMapping")
    public String testControllerWithPath() {
        return "hello world tstGetMapping";
    }
}
