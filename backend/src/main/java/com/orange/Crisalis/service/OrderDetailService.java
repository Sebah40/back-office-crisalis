package com.orange.Crisalis.service;

import com.orange.Crisalis.model.OrderDetail;
import com.orange.Crisalis.repository.OrderDetailRepository;
import com.orange.Crisalis.service.interfaces.IOrderDetailService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class OrderDetailService implements IOrderDetailService {
    private final OrderDetailRepository orderDetailRepository;

    public OrderDetailService(OrderDetailRepository orderDetailRepository) {
        this.orderDetailRepository = orderDetailRepository;
    }

    @Override
    public OrderDetail createDetail(OrderDetail orderDetail) {
        return orderDetailRepository.save(orderDetail);
    }

    @Override
    public List<OrderDetail> saveAllOrderDetail(List<OrderDetail> orderDetailList) {
        return (List<OrderDetail>) orderDetailRepository.saveAll(orderDetailList);
    }

    @Override
    public Optional<List<OrderDetail>> getOrderDetailListByOrderId(Long id) {
        return orderDetailRepository.findOrderDetailByOrderId(id);
    }

}
