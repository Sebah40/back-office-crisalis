package com.orange.Crisalis.exceptions.custom;

public class NullPointerException extends RuntimeException {
    private static final String DESCRIPTION = "Null pointer exception (400). ";

    public NullPointerException(String message) {
        super(DESCRIPTION.concat(message));
    }
}
