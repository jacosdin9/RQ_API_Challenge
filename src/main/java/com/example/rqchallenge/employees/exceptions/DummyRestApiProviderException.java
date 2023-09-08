package com.example.rqchallenge.employees.exceptions;

public class DummyRestApiProviderException extends RuntimeException{

    public DummyRestApiProviderException(String message) {
        super(message);
    }
    public DummyRestApiProviderException(String message, Throwable cause) {
        super(message, cause);
    }
}
