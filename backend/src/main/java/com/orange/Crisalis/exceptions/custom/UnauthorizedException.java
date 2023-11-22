package com.orange.Crisalis.exceptions.custom;


public class UnauthorizedException extends RuntimeException {

    private static final String DESCRIPTION = "Unauthorized request (401). ";

    public UnauthorizedException(String message) {
        super(DESCRIPTION.concat(message));
    }
}
