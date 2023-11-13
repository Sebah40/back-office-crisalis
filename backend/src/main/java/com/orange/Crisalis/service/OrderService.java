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
import java.util.Date;
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
    public Optional<OrderDTO> getOrder(Long id) {
        return Optional.of(new OrderDTO(this.orderRepository.findOrderById(id).orElseThrow(null)));
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
                                ordeD.getQuantity(),
                                ordeD.getSellableGood()
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
    public void editOrder(OrderDTO orderToEdit) {
         OrderEntity order = orderRepository.findById(orderToEdit.getId()).orElseThrow(() -> new RuntimeException("No existe el pedido."));
         List<Long> sellableGoodIdList = order.getOrderDetailList()
                 .stream()
                 .map(orderDetail -> orderDetail
                         .getSellableGood()
                         .getId())
                 .collect(Collectors.toList());

         order.setDateEdited(new Date());

         List<OrderDetailDTO> updatedDetailList = orderToEdit.getOrderDetailDTOList();

         for(OrderDetailDTO updatedDetail : updatedDetailList){
             if(updatedDetail.getId()!=null){
                 OrderDetail existingDetail = orderDetailService.getOrderDetailById(updatedDetail.getId())
                         .orElseThrow(() -> new RuntimeException("El detalle con ID " + updatedDetail.getId() + " no se encontró"));

                 if(updatedDetail.getQuantity() == 0){
                     orderDetailService.deleteOrderDetail(existingDetail);
                 }else {
                     if(updatedDetail.getSellableGood().getType()==Type.SERVICE){
                         existingDetail.setQuantity(1);
                     }else {
                         existingDetail.setQuantity(updatedDetail.getQuantity());
                         orderDetailService.createOrEditDetail(existingDetail);
                     }

                 }

             }else {
                 if(updatedDetail.getQuantity() <= 0){
                     throw new RuntimeException("No se puede agregar producto sin cantidad");
                 }
                 if(sellableGoodIdList.contains(updatedDetail.getSellableGood().getId())){
                     continue;
                 }
                 OrderDetail newDetail = new OrderDetail();
                 newDetail.setSellableGood(updatedDetail.getSellableGood());
                 newDetail.setQuantity(updatedDetail.getQuantity());
                 newDetail.setPriceSell(updatedDetail.getSellableGood().getPrice());
                 newDetail.setOrder(order);
                 orderDetailService.createOrEditDetail(newDetail);
             }

         }


        //Optional<List<OrderDetail>> orderDetails = orderDetailService.getOrderDetailListByOrderId(id);
        // order.getOrderDetailList
    }

    @Override
    public List<OrderDTO> getAllByClientId(Long clientId) {
        return orderRepository.findByClientId(clientId).stream().map(OrderDTO::new).collect(Collectors.toList());
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
