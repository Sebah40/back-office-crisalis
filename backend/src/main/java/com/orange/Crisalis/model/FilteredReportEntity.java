package com.orange.Crisalis.model;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "client_discount_services")
public class FilteredReportEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private ClientEntity client;

    @ManyToOne
    @JoinColumn(name = "sellablegood_id")
    private SellableGood sellableGood;

    @Column(name = "discount")
    private Double discount;



    @Temporal(TemporalType.DATE)
    @Column(name = "order_date")
    private Date orderDate;

    public FilteredReportEntity() {
    }

    public FilteredReportEntity(ClientEntity client, int order, SellableGood sellableGood, Double discount, Date date) {
        this.client = client;
        this.sellableGood = sellableGood;
        this.discount = discount;
        this.orderDate   = date;
    }

    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }

    public ClientEntity getClient() {
        return client;
    }

    public void setClient(ClientEntity client) {
        this.client = client;
    }

    public SellableGood getSellableGood() {
        return sellableGood;
    }

    public void setSellableGood(SellableGood sellableGood) {
        this.sellableGood = sellableGood;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

}