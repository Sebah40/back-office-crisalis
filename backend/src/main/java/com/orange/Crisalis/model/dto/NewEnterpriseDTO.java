package com.orange.Crisalis.model.dto;

import com.orange.Crisalis.model.EnterpriseEntity;
import com.orange.Crisalis.model.PersonEntity;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDate;

public class NewEnterpriseDTO implements Serializable {

    private String businessName;
    private String cuit;
    private LocalDate date;
    private boolean beneficiary;
    private boolean active;

    private String firstNameResponsible;

    private String lastNameResponsible;

    private String dniResponsible;

    public NewEnterpriseDTO() {
    }

    public NewEnterpriseDTO(EnterpriseEntity enterprise){
        this.businessName = enterprise.getBusinessName();
        this.cuit = enterprise.getCuit();
        this.date = enterprise.getDate();
        this.beneficiary = enterprise.isBeneficiary();
        this.firstNameResponsible = enterprise.getFirstNameResponsible();
        this.lastNameResponsible = enterprise.getLastNameResponsible();
        this.dniResponsible = enterprise.getDniResponsible();
    }

    public boolean isActive() {
        return active;
    }

    public String getBusinessName() {
        return businessName;
    }

    public String getCuit() {
        return cuit;
    }

    public LocalDate getDate() {
        return date;
    }

    public boolean isBeneficiary() {
        return beneficiary;
    }

    public String getFirstNameResponsible() {
        return firstNameResponsible;
    }

    public String getLastNameResponsible() {
        return lastNameResponsible;
    }

    public String getDniResponsible() {
        return dniResponsible;
    }
}
