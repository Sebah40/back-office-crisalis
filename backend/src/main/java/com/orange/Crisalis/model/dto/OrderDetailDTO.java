package com.orange.Crisalis.model.dto;

import com.orange.Crisalis.model.OrderDetail;
import lombok.*;
import com.orange.Crisalis.model.SellableGood;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDTO implements Serializable {
    private Long id;
    private Double priceSell;
    private Integer quantity;
    private SellableGood sellableGood;
    private Double discount;

    public OrderDetailDTO(OrderDetail detail) {
        this.id = detail.getId();
        this.priceSell = detail.getPriceSell();
        this.quantity = detail.getQuantity();
        this.sellableGood = detail.getSellableGood();
        this.discount = detail.getDiscount();
    }
}
