package com.orange.Crisalis.model.dto;

import com.orange.Crisalis.model.EnterpriseEntity;

import java.time.LocalDate;

public class NewEnterpriseDTO {

    private String businessName;
    private String cuit;
    private LocalDate date;
    private boolean beneficiary;
    private boolean active;

    public NewEnterpriseDTO() {
    }

    public NewEnterpriseDTO(EnterpriseEntity enterprise){
        this.businessName = enterprise.getBusinessName();
        this.cuit = enterprise.getCuit();
        this.date = enterprise.getDate();
        this.beneficiary = enterprise.isBeneficiary();
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
}
