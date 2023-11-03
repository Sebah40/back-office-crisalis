package com.orange.Crisalis.model.dto;

import com.orange.Crisalis.model.ClientEntity;
import com.orange.Crisalis.model.EnterpriseEntity;
import com.orange.Crisalis.model.PersonEntity;
import lombok.Getter;

import java.io.Serializable;

public class ClientDTO implements Serializable {

    @Getter
    private int id;
    private boolean beneficiary;
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

    public ClientDTO() {
    }

    public ClientDTO(ClientEntity clientEntity) {
        this.id = clientEntity.getId();
        this.beneficiary = clientEntity.isBeneficiary();
    }

    public ClientDTO(ClientEntity clientEntity, EnterpriseEntity enterprise, PersonEntity person) {
        this.id = clientEntity.getId();
        this.beneficiary = clientEntity.isBeneficiary();
        this.businessName = enterprise.getBusinessName();
        this.cuit = enterprise.getCuit();
        this.lastName = person.getLastName();
        this.firstName = person.getFirstName();
        this.dni = person.getDni();
    }

    public int getId() {
        return id;
    }

    public boolean isBeneficiary() {
        return beneficiary;
    }

    public String getBusinessName() {
        return businessName;
    }

    public String getCuit() {
        return cuit;
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
}