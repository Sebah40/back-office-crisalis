package com.orange.Crisalis.model.dto;

import com.orange.Crisalis.model.EnterpriseEntity;

import java.io.Serializable;

public class EnterpriseDTO implements Serializable {

    private String businessName;
    private String cuit;
    private int id;

    public EnterpriseDTO() {
    }

    public EnterpriseDTO(EnterpriseEntity enterprise) {
        this.id = enterprise.getId();
        this.businessName = enterprise.getBusinessName();
        this.cuit = enterprise.getCuit();
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
}