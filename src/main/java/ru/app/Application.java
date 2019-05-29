package ru.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Денис Мироненко
 * @version $Id$
 * @since 29.05.2019
 */

@SpringBootApplication
public class Application {
    public void start() {
        SpringApplication.run(Application.class);
    }

    public static void main(String[] args) {
        new Application().start();
    }
}

