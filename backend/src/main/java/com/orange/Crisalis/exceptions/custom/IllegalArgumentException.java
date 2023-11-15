package com.orange.Crisalis.exceptions.custom;

public class IllegalArgumentException extends RuntimeException {
    private static final String DESCRIPTION = "Illegal argument (400). ";

    public IllegalArgumentException(String message) {
        super(DESCRIPTION.concat(message));
    }
}
