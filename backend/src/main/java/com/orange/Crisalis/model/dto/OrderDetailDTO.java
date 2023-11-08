package com.orange.Crisalis.model.dto;

import com.orange.Crisalis.model.OrderDetail;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDTO implements Serializable {
    private Long id;
    private BigDecimal priceSell;
    private Integer quantity;

    public OrderDetailDTO(OrderDetail detail) {
        this.id = detail.getId();
        this.priceSell = detail.getPriceSell();
        this.quantity = detail.getQuantity();
    }
}
