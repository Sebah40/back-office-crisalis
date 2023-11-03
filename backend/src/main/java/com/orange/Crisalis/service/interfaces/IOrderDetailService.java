package com.orange.Crisalis.service.interfaces;

import com.orange.Crisalis.model.OrderDetail;

import java.util.List;
import java.util.Optional;

public interface IOrderDetailService {
    OrderDetail createDetail(OrderDetail orderDetail);
    List<OrderDetail> saveAllOrderDetail(List<OrderDetail> orderDetailList);
    Optional<List<OrderDetail>> getOrderDetailListByOrderId(Long id);
}
