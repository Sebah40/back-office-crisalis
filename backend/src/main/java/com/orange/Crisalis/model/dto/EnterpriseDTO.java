package com.orange.Crisalis.model.dto;

import com.orange.Crisalis.model.EnterpriseEntity;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDate;

public class EnterpriseDTO implements Serializable {

    private int id;
    private String businessName;
    private String cuit;
    private LocalDate date;
    private String firstNameResponsible;
    private String lastNameResponsible;
    private String dniResponsible;

    public EnterpriseDTO() {
    }

    public EnterpriseDTO(EnterpriseEntity enterprise) {
        this.id = enterprise.getId();
        this.businessName = enterprise.getBusinessName();
        this.cuit = enterprise.getCuit();
        this.date = enterprise.getDate();
        this.firstNameResponsible = enterprise.getFirstNameResponsible();
        this.lastNameResponsible = enterprise.getLastNameResponsible();
        this.dniResponsible = enterprise.getDniResponsible();
    }

    public String getBusinessName() {
        return businessName;
    }

    public String getCuit() {
        return cuit;
    }

    public int getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
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