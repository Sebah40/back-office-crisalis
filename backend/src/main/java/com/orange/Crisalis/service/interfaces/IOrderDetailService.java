package com.orange.Crisalis.service.interfaces;

import com.orange.Crisalis.model.OrderDetail;

import java.util.List;
import java.util.Optional;

public interface IOrderDetailService {
    OrderDetail createOrEditDetail(OrderDetail orderDetail);
    List<OrderDetail> saveAllOrderDetail(List<OrderDetail> orderDetailList);
    Optional<List<OrderDetail>> getOrderDetailListByOrderId(Long id);
    Optional<OrderDetail> getOrderDetailById(Long id);

    void deleteOrderDetail(OrderDetail orderDetail);
}
