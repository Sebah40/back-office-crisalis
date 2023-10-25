package com.orange.Crisalis.model.dto;

import com.orange.Crisalis.model.PersonEntity;

import java.io.Serializable;

public class PersonDTO implements Serializable {

    private String lastName;
    private String firstName;
    private String dni;
    private int id;
    private boolean beneficiary;

    public PersonDTO() {
    }

    public PersonDTO(PersonEntity personEntity) {
        this.lastName = personEntity.getLastName();
        this.firstName = personEntity.getFirstName();
        this.dni = personEntity.getDni();
        this.id = personEntity.getId();
        this.beneficiary = personEntity.isBeneficiary();
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

    public int getId() {
        return id;
    }

    public boolean isBeneficiary() {
        return beneficiary;
    }
}