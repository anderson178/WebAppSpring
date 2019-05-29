package ru.app.exceptions;

public class NoSuchPerson extends RuntimeException {
    public NoSuchPerson(String message) {
        super(message);
    }
}