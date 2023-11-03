package com.orange.Crisalis.dto;

import com.orange.Crisalis.model.Tax;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaxDto implements Serializable {

    private Integer id;

    @NotNull
    private String taxName;
    @NotNull
    private Double taxPercentage;

    public TaxDto(Tax tax) {
        this.id = tax.getId();
        this.taxName = tax.getTaxName();
        this.taxPercentage = tax.getTaxPercentage();
    }
}
