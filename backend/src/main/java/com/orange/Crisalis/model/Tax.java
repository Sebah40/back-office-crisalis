package com.orange.Crisalis.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
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

    @ManyToMany(fetch = FetchType.LAZY,
        cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
        },
        mappedBy = "taxes")
    @JsonIgnore
    @ToString.Exclude
    private Set<SellableGood> sellableGoods = new HashSet<>();

    public Tax(String taxName, Double taxPercentage) {
        this.taxName = taxName;
        this.taxPercentage = taxPercentage;
    }
}
