package com.assignment.flab.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "HelloController")
public class HelloController {

    @GetMapping("/api/hello")
    @ApiOperation(value = "문자열 출력", notes = "print hello world")
    public String hello() {
        return "Hello World.";
    }
}
