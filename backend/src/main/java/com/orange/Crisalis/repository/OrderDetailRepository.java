package com.orange.Crisalis.repository;

import com.orange.Crisalis.model.OrderDetail;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderDetailRepository extends CrudRepository<OrderDetail,Long> {
    @Query(value = "SELECT * FROM order_detail WHERE order_id = :id", nativeQuery = true)
    Optional<List<OrderDetail>> findOrderDetailByOrderId(@Param("id") Long id);
}
