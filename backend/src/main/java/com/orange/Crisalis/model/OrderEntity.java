package com.orange.Crisalis.model;


import com.orange.Crisalis.enums.OrderState;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;


@Data
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="order_entity")
@Entity
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date dateCreated;

    private Date dateEdited;

    @Enumerated(EnumType.STRING)

    private OrderState orderState;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private ClientEntity client;


    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetailList;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private SellableGood service;



    @PrePersist
    public void prePersist(){
        this.dateCreated = new Date();
        this.orderState = OrderState.PENDING;
        this.dateEdited = null;
    }


}
