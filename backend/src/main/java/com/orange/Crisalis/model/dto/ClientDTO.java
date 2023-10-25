package com.orange.Crisalis.model.dto;

import lombok.Getter;

import java.io.Serializable;

public class ClientDTO implements Serializable {

    @Getter
    private int id;
    @Getter
    private String businessName;
    @Getter
    private String cuit;
    @Getter
    private String lastName;
    @Getter
    private String firstName;
    @Getter
    private String dni;
}