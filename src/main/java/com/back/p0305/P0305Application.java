package com.back.p0305;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class P0305Application {

    public static void main(String[] args) {
        SpringApplication.run(P0305Application.class, args);
    }

}
