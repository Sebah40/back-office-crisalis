package com.orange.Crisalis.repository;

import com.orange.Crisalis.model.ClientDiscountServiceEntity;
import com.orange.Crisalis.model.FilteredReportEntity;
import com.orange.Crisalis.model.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByClientId(Long clientId);

    @Query(value = "SELECT * FROM order_entity WHERE id = :id", nativeQuery = true)
    Optional<OrderEntity> findOrderById(@Param("id") Long id);

}
