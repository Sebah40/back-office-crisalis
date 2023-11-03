package com.orange.Crisalis.service;

import com.orange.Crisalis.dto.ProductIdAndQuantityDTO;
import com.orange.Crisalis.dto.RequestBodyCreateOrderDTO;

import com.orange.Crisalis.enums.OrderState;
import com.orange.Crisalis.exceptions.custom.EmptyElementException;
import com.orange.Crisalis.exceptions.custom.NotCancelableException;
import com.orange.Crisalis.exceptions.custom.OrderNotFoundException;
import com.orange.Crisalis.model.*;

import com.orange.Crisalis.model.dto.OrderDTO;
import com.orange.Crisalis.model.dto.OrderDetailDTO;
import com.orange.Crisalis.repository.OrderRepository;
import com.orange.Crisalis.service.interfaces.IOrderService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final ClientService clientService;
    private final SellableGoodService sellableGoodService;
    private final OrderDetailService orderDetailService;

    public OrderService(
            OrderRepository orderRepository,
            ClientService clientService,
            SellableGoodService sellableGoodService,
            OrderDetailService orderDetailService) {
        this.orderRepository = orderRepository;
        this.clientService = clientService;
        this.sellableGoodService = sellableGoodService;
        this.orderDetailService = orderDetailService;
    }

    @Override
    public void createOrder(RequestBodyCreateOrderDTO orderCreateBody)  {

        ClientEntity clientEntity = clientService.getById(orderCreateBody.getClientId());

        if(clientEntity == null){
            throw new EmptyElementException("El cliente no existe.");
        }
        if(orderCreateBody.getProductIdList().isEmpty()){
            throw new EmptyElementException("No hay productos ni servicios para cargar al pedido");
        }

        OrderEntity newOrderEntity = orderRepository.save(new OrderEntity());


        newOrderEntity.setClient(clientEntity);


        List<OrderDetail> orderDetailList = createOrderDetailList(orderCreateBody.getProductIdList(),newOrderEntity);
        newOrderEntity.setOrderDetailList(orderDetailList);



    }

    @Override
    public List<OrderDTO> getOrders() {
        List<OrderDTO> ordersDTO= new ArrayList<>(orderRepository.findAll()).stream().map(
                (orderEntity -> new OrderDTO(
                        orderEntity.getId(),
                        orderEntity.getDateCreated(),
                        orderEntity.getOrderState(),
                        orderEntity.getClient(),
                        orderEntity.getOrderDetailList().stream().map(ordeD -> new OrderDetailDTO(
                                ordeD.getId(),
                                ordeD.getPriceSell(),
                                ordeD.getQuantity()
                                )).collect(Collectors.toList())

                        )
                )
        ).collect(Collectors.toList());
        if (!ordersDTO.isEmpty()){
            return ordersDTO;
        }else{
            throw new EmptyElementException("No hay ordenes para mostrar.");
        }
    }

    @Override
    public void cancelOrder(Long id) {

        Optional<OrderEntity> order = orderRepository.findById(id);
        if(order.isPresent()){
            if(order.get().getOrderState() == OrderState.PENDING){
                order.get().setOrderState(OrderState.CANCELED);
                orderRepository.save(order.get());
            }
            else {
                throw new NotCancelableException("No se puede cancelar el pedido");
            }

        }
        else{
            throw new OrderNotFoundException("No existe el pedido");
        }
    }

    @Override
    public void editOrder(RequestBodyCreateOrderDTO orderToEdit, Long id) {
        Optional<OrderEntity> order = orderRepository.findById(id);
        Optional<List<OrderDetail>> orderDetails = orderDetailService.getOrderDetailListByOrderId(id);

        // order.getOrderDetailList
    }





    private List<OrderDetail> createOrderDetailList(List<ProductIdAndQuantityDTO> productIdAndQuantityDTO, OrderEntity order){
        List<OrderDetail> orderDetailList = new ArrayList<>();
        for (ProductIdAndQuantityDTO p : productIdAndQuantityDTO){
            Optional<SellableGood> sellableGood = sellableGoodService.findById(p.getProductId());
            if(sellableGood.isPresent() && sellableGood.get().getType() == Type.SERVICE){
                p.setQuantity(1);
            }
            orderDetailList.add(new OrderDetail(
                    null,sellableGood.get().getPrice(),
                    p.getQuantity(),sellableGood.get(),order));

        }
        return orderDetailService.saveAllOrderDetail(orderDetailList);


    }
}
