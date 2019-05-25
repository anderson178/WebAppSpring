package ru.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;


@SpringBootApplication
public class Application {
    public void start() {
        SpringApplication.run(Application.class);
    }

    public static void main(String[] args) {
        new Application().start();
    }
}

