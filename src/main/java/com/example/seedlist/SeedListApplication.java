package com.example.seedlist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@EnableJpaAuditing
@SpringBootApplication
public class SeedListApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeedListApplication.class, args);
    }

}
