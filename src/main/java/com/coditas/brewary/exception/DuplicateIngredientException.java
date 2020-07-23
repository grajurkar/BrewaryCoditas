package com.coditas.brewary.exception;

public class DuplicateIngredientException extends RuntimeException {
    public DuplicateIngredientException(String errorMessage) {
        super(errorMessage);
    }
}
