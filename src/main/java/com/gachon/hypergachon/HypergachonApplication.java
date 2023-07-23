package com.gachon.hypergachon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class HypergachonApplication {

    public static void main(String[] args) {
        SpringApplication.run(HypergachonApplication.class, args);
    }

}
