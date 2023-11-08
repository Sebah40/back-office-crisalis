package com.orange.Crisalis.model;

import com.orange.Crisalis.model.ClientEntity;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@DiscriminatorValue("ENTERPRISE")
public class EnterpriseEntity extends ClientEntity {

    @NotNull
    private String businessName;
    @NotNull
    private String cuit;
    @NotNull
    private LocalDate date;
    private boolean isActive;
    @Getter
    private String firstNameResponsible;
    @Getter
    private String lastNameResponsible;
    @Getter
    private String dniResponsible;

/*    @Getter
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private PersonEntity personEntity;*/

    public EnterpriseEntity() {
    }

    public EnterpriseEntity(boolean beneficiary, String businessName, String cuit, LocalDate date, boolean isActive, String firstNameResponsible, String lastNameResponsible, String dniResponsible) {
        super(beneficiary);
        this.businessName = businessName;
        this.cuit = cuit;
        this.date = date;
        this.isActive = isActive;
        this.firstNameResponsible = firstNameResponsible;
        this.lastNameResponsible = lastNameResponsible;
        this.dniResponsible = dniResponsible;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setFirstNameResponsible(String firstNameResponsible) {
        this.firstNameResponsible = firstNameResponsible;
    }

    public void setLastNameResponsible(String lastNameResponsible) {
        this.lastNameResponsible = lastNameResponsible;
    }

    public void setDniResponsible(String dniResponsible) {
        this.dniResponsible = dniResponsible;
    }
}
