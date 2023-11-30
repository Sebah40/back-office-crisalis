package com.orange.Crisalis.service.interfaces;

import com.orange.Crisalis.enums.Type;
import com.orange.Crisalis.model.*;


import java.util.List;

public interface ICalculationEngine {
    static Double calculateValueWarranty(OrderDetail detail){
        final double PERCENTAGE_PER_YEAR = .02;
        return (detail.getSellableGood().getPrice().doubleValue() * PERCENTAGE_PER_YEAR) * detail.getWarrantyYear();
    }
   static Double priceWithTaxes(SellableGood sellableGood){

       final double BASE_PRICE = sellableGood.getPrice().doubleValue();

       double priceSell = BASE_PRICE;

       double taxSum = sellableGood.getTaxes()
               .stream()
               .mapToDouble(Tax::getTaxPercentage)
               .sum();

       if(taxSum == 0){
           return priceSell;
       }
       

       priceSell += (BASE_PRICE * taxSum) / 100;


       return priceSell;
   }

   static List<OrderDetail> generateDiscount (List<OrderDetail> orderDetailList){
        final double TOTAL_DISCOUNT = 2500;
        final int DISCOUNT_PERCENTAGE = 10;
        double availableDiscount = TOTAL_DISCOUNT;

        for(OrderDetail detail : orderDetailList){
            if(availableDiscount <= 0){
                detail.setDiscount(.0);
                continue;
            }
            if(detail.getSellableGood().getType() != Type.SERVICE){
                double discount = (detail.getPriceSell() * DISCOUNT_PERCENTAGE / 100)* detail.getQuantity();
                detail.setDiscount( Math.min(discount,availableDiscount) );
                availableDiscount -= discount;
            }

        }
        return orderDetailList;
   }

   static Double generateDiscount(OrderEntity order){
       return order.getOrderDetailList().stream().mapToDouble(OrderDetail::getDiscount).sum();
   }

   static Double generateSubTotal(OrderDetail detail){
        return (
                detail.getQuantity() * detail.getSellableGood().getPrice().doubleValue());
//                + detail.getSellableGood().getSupportCharge().doubleValue()
//                + calculateValueWarranty(detail);
   }

   static Double generateTotalOrderDetail(OrderDetail orderDetail) {
       double taxesByUnit = (orderDetail.getSellableGood().getTaxes().stream().mapToDouble(Tax::getTaxPercentage).sum() * orderDetail.getSellableGood().getPrice().doubleValue()) / 100;
       double subtotalWithTaxWarrantyAndSupportCharge = orderDetail.getSellableGood().getPrice().doubleValue() + taxesByUnit + ICalculationEngine.calculateValueWarranty(orderDetail) + orderDetail.getSellableGood().getSupportCharge().doubleValue();
       return orderDetail.getQuantity() * subtotalWithTaxWarrantyAndSupportCharge - orderDetail.getDiscount();
   }
   static Double generateSubTotal(OrderEntity order){
       return order.getOrderDetailList()
               .stream()
               .mapToDouble(detail ->
                       (detail.getPriceSell () * detail.getQuantity())
                               + detail.getSellableGood().getSupportCharge().doubleValue()
                               + calculateValueWarranty(detail)

               )
               .sum();
   }

   static Double generateSubTotalWithDiscount(OrderDetail detail){
       return generateSubTotal(detail) - detail.getDiscount();
   }

   static Double totalOrderPrice(OrderEntity order){
       return generateSubTotal(order) - generateDiscount(order);
   }

}
