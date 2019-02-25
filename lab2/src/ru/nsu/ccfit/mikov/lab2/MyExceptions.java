package ru.nsu.ccfit.mikov.lab2;

public class MyExceptions extends Exception {
    private final String message;

    public MyExceptions(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
