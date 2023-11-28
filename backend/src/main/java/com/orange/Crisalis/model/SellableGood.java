package com.orange.Crisalis.model;

import com.orange.Crisalis.enums.Type;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "sellable_good")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SellableGood {
  @Id
  @GeneratedValue(
      strategy = GenerationType.IDENTITY
  )
  private Long id;

  @Column(unique=true)
  @NotNull
  private String name;

  private String description;
  @NotNull
  private BigDecimal price;

  private BigDecimal supportCharge;

  private boolean isActive;

  @NotNull
  @Enumerated(EnumType.STRING)
  private Type type;

  @ManyToMany(fetch = FetchType.LAZY,
      cascade = {
          CascadeType.PERSIST,
          CascadeType.MERGE
      })
  @JoinTable(name = "sellablegoods_taxes",
      joinColumns = { @JoinColumn(name = "sellablegood_id") },
      inverseJoinColumns = { @JoinColumn(name = "tax_id") })
  @ToString.Exclude
  private Set<Tax> taxes = new HashSet<>();

  public void addTax(Tax tax) {
    this.taxes.add(tax);
    tax.getSellableGoods().add(this);
  }

  public void removeTax(int taxId) {
    Tax tax = this.taxes.stream().filter(t -> t.getId() == taxId).findFirst().orElse(null);
    if (tax != null) {
      this.taxes.remove(tax);
      tax.getSellableGoods().remove(this);
    }
  }
}
