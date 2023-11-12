package com.orange.Crisalis.service.interfaces;

import com.orange.Crisalis.model.SellableGood;
import com.orange.Crisalis.model.Tax;


import java.util.List;
import java.util.stream.Collectors;

public interface ICalculationEngine {
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
}
