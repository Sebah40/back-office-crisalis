package com.orange.Crisalis.repository;

import com.orange.Crisalis.model.SellableGood;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SellableGoodRepository extends JpaRepository<SellableGood, Long> {
  List<SellableGood> findSellableGoodByTaxesId(Integer taxId);
}