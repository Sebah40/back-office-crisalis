package com.orange.Crisalis.repository;

import com.orange.Crisalis.model.OrderDetail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends CrudRepository<OrderDetail,Long> {
}
