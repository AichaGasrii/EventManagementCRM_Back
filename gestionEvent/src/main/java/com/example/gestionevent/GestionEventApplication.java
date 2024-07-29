package com.example.gestionevent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.gestionevent.repositories")

public class GestionEventApplication {

    public static void main(String[] args) {
        SpringApplication.run(GestionEventApplication.class, args);
    }

}
