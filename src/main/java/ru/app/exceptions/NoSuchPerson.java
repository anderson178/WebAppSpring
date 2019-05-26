package ru.app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class NoSuchPerson extends RuntimeException {
    public NoSuchPerson(String message) {
        super(message);
    }
}