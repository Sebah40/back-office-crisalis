package com.orange.Crisalis.exceptions.custom;

public class NotCancelableException extends RuntimeException{
    private static final String DESCRIPTION = "Empty element (400). ";

    public NotCancelableException(String detail) {
        super(DESCRIPTION.concat(detail));
    }
}