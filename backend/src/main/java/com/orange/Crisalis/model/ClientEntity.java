package com.orange.Crisalis.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
public class ClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "name")
    @GenericGenerator(name="name", strategy="native")
    private int id;

    @NotNull
    private boolean beneficiary;

    @OneToMany(mappedBy="client", fetch= FetchType.EAGER)
    Set<OrderEntity> orderEntitySet = new HashSet<>();
    @ManyToMany
    @JoinTable(
            name = "client_sellablegood",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "sellablegood_id")
    )
    private Set<SellableGood> activeServices = new HashSet<>();

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    Set<ClientDiscountServiceEntity> discountServices = new HashSet<>();

    @JsonIgnore
    public Set<ClientDiscountServiceEntity> getDiscountServices() {
        return discountServices;
    }

    public void addDiscountService(SellableGood service, OrderEntity order, Double discount, Date date) {
        ClientDiscountServiceEntity newEntry = new ClientDiscountServiceEntity(this,order,service, discount, date);
        this.discountServices.add(newEntry);
    }

    public ClientEntity() {
    }

    public ClientEntity(boolean beneficiary) {
        this.beneficiary = beneficiary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isBeneficiary() {
        return beneficiary;
    }

    public void setBeneficiary(boolean beneficiary) {
        this.beneficiary = beneficiary;
    }
    public Set<SellableGood> getActiveServices() {
        return activeServices;
    }

    public void setActiveServices(Set<SellableGood> activeServices) {
        this.activeServices = activeServices;
    }
}