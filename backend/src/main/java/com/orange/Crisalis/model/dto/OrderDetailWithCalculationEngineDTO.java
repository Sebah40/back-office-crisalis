package com.orange.Crisalis.model.dto;

import com.orange.Crisalis.model.OrderDetail;
import com.orange.Crisalis.model.SellableGood;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class OrderDetailWithCalculationEngineDTO extends OrderDetailDTO {
    private Double supportCharge;
    private Double warrantyValue;
    private Double discount;
    private Double subTotalWithoutDiscount;
    private Double subTotal;

    public OrderDetailWithCalculationEngineDTO(Long id, Double priceSell, Integer quantity, SellableGood sellableGood, Double supportCharge, Double warrantyValue, Double discount, Double subTotalWithoutDiscount, Double subTotal) {
        super(id, priceSell, quantity, sellableGood, discount);
        this.supportCharge = supportCharge;
        this.warrantyValue = warrantyValue;
        this.discount = discount;
        this.subTotalWithoutDiscount = subTotalWithoutDiscount;
        this.subTotal = subTotal;
    }

    public OrderDetailWithCalculationEngineDTO(Double supportCharge, Double warrantyValue, Double discount, Double subTotalWithoutDiscount, Double subTotal) {
        this.supportCharge = supportCharge;
        this.warrantyValue = warrantyValue;
        this.discount = discount;
        this.subTotalWithoutDiscount = subTotalWithoutDiscount;
        this.subTotal = subTotal;
    }

    public OrderDetailWithCalculationEngineDTO(OrderDetail detail, Double supportCharge, Double warrantyValue, Double discount, Double subTotalWithoutDiscount, Double subTotal) {
        super(detail);
        this.supportCharge = supportCharge;
        this.warrantyValue = warrantyValue;
        this.discount = discount;
        this.subTotalWithoutDiscount = subTotalWithoutDiscount;
        this.subTotal = subTotal;
    }
}
