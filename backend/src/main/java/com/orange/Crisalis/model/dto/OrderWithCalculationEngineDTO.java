package com.orange.Crisalis.model.dto;

import com.orange.Crisalis.enums.OrderState;
import com.orange.Crisalis.model.ClientEntity;
import lombok.*;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Data
public class OrderWithCalculationEngineDTO {
    private Long id;
    private Date dateCreated;
    private OrderState orderState;
    private ClientEntity client;
    private List<OrderDetailWithCalculationEngineDTO> orderDetailList;
    private Double discount;
    private Double subTotalWithoutDiscount;
    private Double total;

}
