package com.gachon.hypergachon.global.common;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HealthCheckApi {

    @GetMapping
    public String healthCheckApi(){
        return "201935079 오진영";
    }
}
