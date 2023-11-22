package com.orange.Crisalis.model.dto;

import com.orange.Crisalis.enums.OrderState;
import com.orange.Crisalis.model.ClientEntity;

import com.orange.Crisalis.model.OrderEntity;
import com.orange.Crisalis.model.SellableGood;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO implements Serializable {
    private Long id;
    private Date dateCreated;
    private OrderState orderState;
    private ClientEntity client;
    private SellableGood service;
    private List<OrderDetailDTO> orderDetailDTOList;

    public OrderDTO(OrderEntity order) {
        this.id = order.getId();
        this.client = order.getClient();
        this.orderState = order.getOrderState();
        this.dateCreated = order.getDateCreated();
        this.orderDetailDTOList = order.getOrderDetailList().stream().map(OrderDetailDTO::new).collect(Collectors.toList());
        this.service = order.getService();
    }

}