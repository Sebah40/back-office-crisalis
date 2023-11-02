package com.orange.Crisalis.model.dto;

import com.orange.Crisalis.enums.OrderState;
import com.orange.Crisalis.model.ClientEntity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Data
@Getter
@Setter

public class OrderDTO {
    private Long id;
    private Date dateCreated;
    private OrderState orderState;
    private ClientEntity client;
    private List<OrderDetailDTO> orderDetailDTOList;

    public OrderDTO(Long id, Date dateCreated, OrderState orderState, ClientEntity client, List<OrderDetailDTO> orderDetailDTOList) {

        this.id = id;
        this.dateCreated = dateCreated;
        this.orderState = orderState;
        this.client = client;
        this.orderDetailDTOList = orderDetailDTOList;
    }
}
