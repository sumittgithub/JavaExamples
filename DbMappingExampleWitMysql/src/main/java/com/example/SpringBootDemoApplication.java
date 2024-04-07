package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.example")
@EntityScan("com.example")
@ComponentScan("com.example")

public class SpringBootDemoApplication {

    public static void main(String[] args) { 
        SpringApplication.run(SpringBootDemoApplication.class, args);
    }
}
