package ru.nsu.ccfit.mikov.lab3;

public class MyExceptions extends Exception {
    private final String message;

    MyExceptions(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}