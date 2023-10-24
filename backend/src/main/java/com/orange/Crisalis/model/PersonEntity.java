package com.orange.Crisalis.model;
import com.orange.Crisalis.model.ClientEntity;
import javax.persistence.Entity;

@Entity
public class PersonEntity extends ClientEntity {

    private String lastName;
    private String firstName;
    private String dni;

    public PersonEntity() {
    }

    public PersonEntity(int id, boolean beneficiary, String lastName, String firstName, String dni) {
        super(id, beneficiary);
        this.lastName = lastName;
        this.firstName = firstName;
        this.dni = dni;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }
}