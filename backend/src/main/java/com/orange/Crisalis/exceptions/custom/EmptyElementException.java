package com.orange.Crisalis.exceptions.custom;

public class EmptyElementException extends RuntimeException{
    private static final String DESCRIPTION = "Empty element (400). ";

    public EmptyElementException(String detail) {
        super(DESCRIPTION.concat(detail));
    }
}