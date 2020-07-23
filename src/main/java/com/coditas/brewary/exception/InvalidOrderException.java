package com.coditas.brewary.exception;

public class InvalidOrderException extends RuntimeException {

   
    public InvalidOrderException(String errorMessage) {
        super(errorMessage);
    }
}
