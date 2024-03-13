package com.example.hibernatereactive;

import jakarta.persistence.Persistence;
import org.hibernate.reactive.mutiny.Mutiny;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HibernateReactiveApplication {

    public static void main(String[] args) {
        SpringApplication.run(HibernateReactiveApplication.class, args);
    }
}
