package com.orange.Crisalis.exceptions.custom;

public class NotFoundException extends RuntimeException {

    private static final String DESCRIPTION = "Element not found (404). ";

    public NotFoundException(String message) {
        super(DESCRIPTION.concat(message));
    }
}
