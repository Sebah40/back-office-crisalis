package com.orange.Crisalis.repository;

import com.orange.Crisalis.model.ClientDiscountServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface ClientDiscountServiceRepository extends JpaRepository<ClientDiscountServiceEntity, Integer> {
    List<ClientDiscountServiceEntity> findByOrderDateBetween(Date startDate, Date endDate);
}