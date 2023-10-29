package com.orange.Crisalis.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Tax {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String taxName;

    @NotNull
    private Double taxPercentage;

    private boolean isActive = true;

    @OneToMany(mappedBy = "tax")
    @Getter(value = AccessLevel.NONE)
    private Set<ItemTax> itemTaxes;

    public Tax(String taxName, Double taxPercentage) {
        this.taxName = taxName;
        this.taxPercentage = taxPercentage;
    }
}
