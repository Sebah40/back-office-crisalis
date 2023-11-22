package com.orange.Crisalis.model.dto;

import com.orange.Crisalis.model.PersonEntity;

import java.io.Serializable;

public class PersonDTO implements Serializable {

    private int id;
    private String firstName;
    private String lastName;
    private String dni;
    private boolean beneficiary;
    private boolean client;

    public PersonDTO() {
    }

    public PersonDTO(PersonEntity personEntity) {
        this.id = personEntity.getId();
        this.lastName = personEntity.getLastName();
        this.firstName = personEntity.getFirstName();
        this.dni = personEntity.getDni();
        this.beneficiary = personEntity.isBeneficiary();
    }

    public int getId() {
        return id;
    }
    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getDni() {
        return dni;
    }


    public boolean isBeneficiary() {
        return beneficiary;
    }
}