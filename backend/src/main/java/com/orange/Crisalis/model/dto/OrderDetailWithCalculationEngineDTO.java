package com.orange.Crisalis.model.dto;

import com.orange.Crisalis.model.OrderDetail;
import com.orange.Crisalis.model.SellableGood;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class OrderDetailWithCalculationEngineDTO extends OrderDetailDTO {
    private Double discount;
    private Double subTotalWithoutDiscount;
    private Double subTotal;

    public OrderDetailWithCalculationEngineDTO(Long id, Double priceSell, Integer quantity, SellableGood sellableGood, Double discount, Double subTotalWithoutDiscount, Double subTotal) {
        super(id, priceSell, quantity, sellableGood);
        this.discount = discount;
        this.subTotalWithoutDiscount = subTotalWithoutDiscount;
        this.subTotal = subTotal;
    }

    public OrderDetailWithCalculationEngineDTO(Double discount, Double subTotalWithoutDiscount, Double subTotal) {
        this.discount = discount;
        this.subTotalWithoutDiscount = subTotalWithoutDiscount;
        this.subTotal = subTotal;
    }

    public OrderDetailWithCalculationEngineDTO(OrderDetail detail, Double discount, Double subTotalWithoutDiscount, Double subTotal) {
        super(detail);
        this.discount = discount;
        this.subTotalWithoutDiscount = subTotalWithoutDiscount;
        this.subTotal = subTotal;
    }
}
