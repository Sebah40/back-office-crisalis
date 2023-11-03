package com.orange.Crisalis.exceptions.custom;

public class OrderNotFoundException extends RuntimeException{
    private static final String DESCRIPTION = "Empty element (400). ";

    public OrderNotFoundException(String detail) {
        super(DESCRIPTION.concat(detail));
    }
}
