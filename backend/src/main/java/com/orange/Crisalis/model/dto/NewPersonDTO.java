package com.orange.Crisalis.model.dto;

import com.orange.Crisalis.model.PersonEntity;

public class NewPersonDTO {

    private String lastName;
    private String firstName;
    private String dni;
    private boolean beneficiary;
    private boolean active;
    private boolean client;

    public NewPersonDTO() {
    }

    public NewPersonDTO(PersonEntity person){
        this.lastName = person.getLastName();
        this.firstName = person.getFirstName();
        this.dni = person.getDni();
        this.beneficiary = person.isBeneficiary();
        this.active = person.isActive();
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

    public boolean isActive() {
        return active;
    }

}
