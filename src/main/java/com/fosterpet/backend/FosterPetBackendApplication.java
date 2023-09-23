package com.fosterpet.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class FosterPetBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(FosterPetBackendApplication.class, args);
    }

}
