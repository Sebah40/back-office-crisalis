package com.orange.Crisalis.service;

import com.orange.Crisalis.dto.DiscountServiceDTO;
import com.orange.Crisalis.dto.FilteredReportDTO;
import com.orange.Crisalis.dto.ProductIdAndQuantityDTO;
import com.orange.Crisalis.dto.RequestBodyCreateOrderDTO;

import com.orange.Crisalis.enums.OrderState;
import com.orange.Crisalis.enums.Type;
import com.orange.Crisalis.exceptions.custom.EmptyElementException;
import com.orange.Crisalis.exceptions.custom.NotCancelableException;
import com.orange.Crisalis.exceptions.custom.OrderNotFoundException;
import com.orange.Crisalis.model.*;

import com.orange.Crisalis.model.dto.OrderDTO;
import com.orange.Crisalis.model.dto.OrderDetailDTO;
import com.orange.Crisalis.model.dto.OrderDetailWithCalculationEngineDTO;
import com.orange.Crisalis.model.dto.OrderWithCalculationEngineDTO;
import com.orange.Crisalis.repository.*;
import com.orange.Crisalis.model.dto.filters.OrderFilter;
import com.orange.Crisalis.queries.OrderQueries;
import com.orange.Crisalis.service.interfaces.ICalculationEngine;
import com.orange.Crisalis.service.interfaces.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ValidationException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final ClientService clientService;
    private final SellableGoodService sellableGoodService;
    private final OrderDetailService orderDetailService;

    @Autowired
    IEnterpriseRepository iEnterpriseRepository;
    @Autowired
    IClientRepository iClientRepository;
    @Autowired
    IPersonRepository iPersonRepository;
    @Autowired
    ClientDiscountServiceRepository discountServiceRepository;
    @Autowired
    FilteredReportRepository filteredReportRepository;


    private final OrderQueries orderQueries;

    @PersistenceContext
    EntityManager entityManager;

    public OrderService(
            OrderRepository orderRepository,
            ClientService clientService,
            SellableGoodService sellableGoodService,
            OrderDetailService orderDetailService, OrderQueries orderQueries) {
        this.orderRepository = orderRepository;
        this.clientService = clientService;
        this.sellableGoodService = sellableGoodService;
        this.orderDetailService = orderDetailService;
        this.orderQueries = orderQueries;
    }

    @Override
    public Optional<OrderDTO> getOrder(Long id) {
        return Optional.of(new OrderDTO(this.orderRepository.findOrderById(id).orElseThrow(null)));
    }

    @Override
    public OrderWithCalculationEngineDTO getOrderWithCalculation(Long id) {
        OrderWithCalculationEngineDTO orderWithCalculationDTO = this.orderRepository.findOrderById(id).map(
            (orderEntity -> new OrderWithCalculationEngineDTO(
                orderEntity.getId(),
                orderEntity.getDateCreated(),
                orderEntity.getOrderState(),
                orderEntity.getClient(),
                orderEntity.getOrderDetailList().stream().map(detail -> new OrderDetailWithCalculationEngineDTO(
                    detail.getId(),
                    detail.getPriceSell(),
                    detail.getQuantity(),
                    detail.getSellableGood(),
                    detail.getSellableGood().getSupportCharge().doubleValue(),
                    ICalculationEngine.calculateValueWarranty(detail),
                    detail.getDiscount(),
                    ICalculationEngine.generateSubTotal(detail),
                    ICalculationEngine.generateSubTotalWithDiscount(detail)
                )).collect(Collectors.toList()),
                ICalculationEngine.generateDiscount(orderEntity),
                ICalculationEngine.generateSubTotal(orderEntity),
                ICalculationEngine.totalOrderPrice(orderEntity)
            ))).orElse(null);
        System.out.println(orderWithCalculationDTO);
        return orderWithCalculationDTO;
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

        List<OrderDetail> orderDetailList = createOrderDetailList(orderCreateBody.
                        getProductIdList(),newOrderEntity);

        if(clientEntity.isBeneficiary() && !clientEntity.getActiveServices().isEmpty()) {
            Set<SellableGood> activeServices = clientEntity.getActiveServices();
            newOrderEntity.setService(getRandomService(activeServices));
            ICalculationEngine.generateDiscount(orderDetailList);
        }else if(orderAService(orderDetailList)){
            SellableGood orderedService = getOrderedService(orderDetailList);
            newOrderEntity.setService(orderedService);
            ICalculationEngine.generateDiscount(orderDetailList);
        }

        newOrderEntity.setOrderDetailList(orderDetailList);
    }

    private SellableGood getOrderedService(List<OrderDetail> orderDetailList) {
        Optional<OrderDetail> orderedService = orderDetailList.stream().filter(od -> od.getSellableGood().getType() == Type.SERVICE).findFirst();
        return orderedService.map(OrderDetail::getSellableGood).orElse(null);
    }

    private boolean orderAService(List<OrderDetail> orderDetailList) {
        return orderDetailList.stream().anyMatch(od -> od.getSellableGood().getType() == Type.SERVICE );
    }


    private SellableGood getRandomService(Set<SellableGood> activeServices) {
        if(activeServices.isEmpty()) {
            return null;
        }
        List<SellableGood> services = new ArrayList<>(activeServices);
        return services.get(0);
    }

    @Override
    public List<OrderDTO> getOrders() {
        List<OrderDTO> ordersDTO = new ArrayList<>(orderRepository.findAll()).stream().map(
                (orderEntity -> new OrderDTO(
                        orderEntity.getId(),
                        orderEntity.getDateCreated(),
                        orderEntity.getOrderState(),
                        orderEntity.getClient(),
                        orderEntity.getService(),
                        orderEntity.getOrderDetailList().stream().map(ordeD -> new OrderDetailDTO(
                                ordeD.getId(),
                                ordeD.getPriceSell(),
                                ordeD.getQuantity(),
                                ordeD.getSellableGood(),
                                ordeD.getDiscount()
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
    public List<OrderWithCalculationEngineDTO> getOrdersWithSubTotal() {
        List<OrderWithCalculationEngineDTO> ordersDTO = new ArrayList<>(orderRepository.findAll()).stream().map(
                (orderEntity -> new OrderWithCalculationEngineDTO(
                        orderEntity.getId(),
                        orderEntity.getDateCreated(),
                        orderEntity.getOrderState(),
                        orderEntity.getClient(),
                        orderEntity.getOrderDetailList().stream().map(detail -> new OrderDetailWithCalculationEngineDTO(
                                detail.getId(),
                                detail.getPriceSell(),
                                detail.getQuantity(),
                                detail.getSellableGood(),
                                detail.getSellableGood().getSupportCharge().doubleValue(),
                                ICalculationEngine.calculateValueWarranty(detail),
                                detail.getDiscount(),
                                ICalculationEngine.generateSubTotal(detail),
                                ICalculationEngine.generateSubTotalWithDiscount(detail)

                        )).collect(Collectors.toList()),
                        ICalculationEngine.generateDiscount(orderEntity),
                        ICalculationEngine.generateSubTotal(orderEntity),
                        ICalculationEngine.totalOrderPrice(orderEntity)


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
    public List<OrderDTO> filterOrderList(OrderFilter orderFilter) {
        List<OrderEntity> orderList = entityManager.createNativeQuery(orderQueries.filterOrderList(orderFilter)
                , OrderEntity.class).getResultList();
        List<OrderDTO> orderDTOList = orderList.stream().map(OrderDTO::new).collect(Collectors.toList());
        return orderDTOList;
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
    public void validateOrder(Long id) {
        Optional<OrderEntity> orderOptional = orderRepository.findById(id);

        if (orderOptional.isPresent()) {
            OrderEntity order = orderOptional.get();

            if (order.getOrderState() == OrderState.PENDING) {
                order.setOrderState(OrderState.FINISHED);
                for (OrderDetail orderDetail : order.getOrderDetailList()) {
                    SellableGood sellableGood = orderDetail.getSellableGood();
                    if (sellableGood.getType() == Type.SERVICE) {
                        order.getClient().getActiveServices().add(sellableGood);
                        order.getClient().setBeneficiary(true);
                        clientService.saveClient(order.getClient());
                    }
                    if(order.getClient().isBeneficiary()){
                        SellableGood firstService = order.getClient().getActiveServices().stream().findFirst().get();
                        Double discount = ICalculationEngine.generateDiscount(order);
                        order.getClient().addDiscountService(firstService,order,discount, new Date());
                    }
                }
                orderRepository.save(order);

            } else {
                throw new ValidationException("No se puede validar el pedido");
            }
        } else {
            throw new OrderNotFoundException("No existe el pedido");
        }
    }

    public Set<FilteredReportDTO> getDTO(Date startDate, Date endDate, String sellableGood, Integer clientID) {
        return getAllSellableGoodOrders(startDate, endDate).stream()
                .filter(entity -> (clientID == null || clientID.equals(orderRepository.findById((long) entity.getId())
                        .map(order -> order.getClient().getId()).orElse(null))))
                .flatMap(entity -> orderRepository.findOrderById((long) entity.getId())
                        .map(order -> order.getOrderDetailList().stream()
                                .filter(orderDetail -> sellableGood == null || sellableGood.equals(orderDetail.getSellableGood().getName()))
                                .map(orderDetail -> {
                                    FilteredReportDTO dto = new FilteredReportDTO();
                                    dto.setOrderDate(entity.getOrderDate());
                                    if (clientID == null || clientID.equals(order.getClient().getId())) {
                                        dto.setOrderStatus(order.getOrderState());
                                        dto.setClientID(order.getClient().getId());
                                        dto.setOrderID(orderDetail.getOrder().getId().intValue());
                                        dto.setSellableGood(orderDetail.getSellableGood().getName());
                                        dto.setSupportCharge(orderDetail.getSellableGood().getSupportCharge().doubleValue());
                                        dto.setWarrantyValue(ICalculationEngine.calculateValueWarranty(orderDetail));
                                        dto.setPrice(orderDetail.getSellableGood().getPrice().doubleValue());
                                        dto.setDiscount(orderDetail.getDiscount());
                                        if (dto.getDiscount() < 0)
                                            dto.setDiscount(0.0);
                                        dto.setSubtotal(ICalculationEngine.generateSubTotal(orderDetail));
                                        dto.setTotal(ICalculationEngine.generateTotalOrderDetail(orderDetail));
                                        dto.setQuantity(orderDetail.getQuantity());
                                        double totalTaxes = orderDetail.getSellableGood().getTaxes().stream()
                                                .mapToDouble(tax -> {
                                                    double taxByUnit = (tax.getTaxPercentage() * orderDetail.getSellableGood().getPrice().doubleValue()) / 100;
                                                    return taxByUnit * dto.getQuantity();
                                                })
                                                .sum();
                                        dto.setTaxes(totalTaxes);
                                        iPersonRepository.findById(dto.getClientID())
                                                .ifPresent(person -> dto.setClientName(person.getFirstName() + " " + person.getLastName()));
                                        iEnterpriseRepository.findById(dto.getClientID())
                                                .ifPresent(company -> dto.setClientName(company.getBusinessName()));
                                        return dto;
                                    } else {
                                        return null;
                                    }
                                })
                        )
                        .orElse(null)
                )
                .collect(Collectors.toSet());
    }

    public Set<ClientDiscountServiceEntity> getAllDiscountServices(Date startDate, Date endDate) {
        List<ClientDiscountServiceEntity> discountServicesInRange = discountServiceRepository.findByOrderDateBetween(startDate, endDate);
        return discountServicesInRange.stream()
                .collect(Collectors.toSet());
    }
    public Set<FilteredReportEntity> getAllSellableGoodOrders(Date startDate, Date endDate) {
        List<FilteredReportEntity> productsAndServicesInRange = filteredReportRepository.findByOrderDateBetween(startDate, endDate);
        return productsAndServicesInRange.stream()
                .collect(Collectors.toSet());
    }
    @Override
    public void editOrder(OrderDTO orderToEdit) {
         OrderEntity order = orderRepository.findById(orderToEdit.getId()).orElseThrow(() -> new RuntimeException("No existe el pedido."));
         if (order.getOrderState() == OrderState.FINISHED || order.getOrderState() == OrderState.CANCELED ){
             throw new RuntimeException("no se puede editar un pedido que esta cancelado o finalizado");
         }
         order.setDateEdited(new Date());

         List<OrderDetailDTO> updatedDetailList = orderToEdit.getOrderDetailDTOList();

         for(OrderDetailDTO updatedDetail : updatedDetailList){
             if(updatedDetail.getId()!=null){
                 OrderDetail existingDetail = orderDetailService.getOrderDetailById(updatedDetail.getId())
                         .orElseThrow(() -> new RuntimeException("El detalle con ID " + updatedDetail.getId() + " no se encontró"));

                 if(updatedDetail.getQuantity() == 0){
                     orderDetailService.deleteOrderDetail(existingDetail);
                 }
                 else{
                     if(updatedDetail.getSellableGood().getType() == Type.SERVICE){
                         existingDetail.setQuantity(1);

                     }
                     else{
                         existingDetail.setQuantity(updatedDetail.getQuantity());
                     }
                         orderDetailService.createOrEditDetail(existingDetail);

                 }

             }else {
                 if(updatedDetail.getQuantity() <= 0){
                     throw new RuntimeException("no se puede agregar producto sin cantidad");
                 }

                 Double priceSell = ICalculationEngine.priceWithTaxes(updatedDetail.getSellableGood());

                 OrderDetail newDetail = new OrderDetail();

                 newDetail.setSellableGood(updatedDetail.getSellableGood());
                 newDetail.setQuantity(updatedDetail.getSellableGood().getType() == Type.SERVICE
                         ? 1
                         : updatedDetail.getQuantity());
                 newDetail.setPriceSell(priceSell);
                 newDetail.setOrder(order);
                 orderDetailService.createOrEditDetail(newDetail);
             }
         }

         if(order.getService() != null){
             ICalculationEngine.generateDiscount(order.getOrderDetailList());
         }

         orderDetailService.saveAllOrderDetail(order.getOrderDetailList());
    }
    @Override
    public List<OrderDTO> getAllByClientId(Long clientId) {
        return orderRepository.findByClientId(clientId).stream().map(OrderDTO::new).collect(Collectors.toList());
    }





    private List<OrderDetail> createOrderDetailList(List<ProductIdAndQuantityDTO> productIdAndQuantityDTO, OrderEntity order){
        List<OrderDetail> orderDetailList = new ArrayList<>();
        for (ProductIdAndQuantityDTO item : productIdAndQuantityDTO){
            Optional<SellableGood> sellableGood = sellableGoodService.findById(item.getProductId());
            if(sellableGood.isPresent()){

                Double priceSell = ICalculationEngine.priceWithTaxes(sellableGood.get());
                OrderDetail newDetail = new OrderDetail();

                newDetail.setPriceSell(priceSell);
                newDetail.setQuantity(item.getQuantity());
                newDetail.setSellableGood(sellableGood.get());
                newDetail.setOrder(order);

                if(item.getWarrantyYear() == null || sellableGood.get().getType() == Type.SERVICE){
                    newDetail.setWarrantyYear(0);
                }else{
                    newDetail.setWarrantyYear(item.getWarrantyYear());
                }
                if(sellableGood.get().getType() == Type.SERVICE){
                    newDetail.setQuantity(1);
                }

                orderDetailList.add(newDetail);
            }



        }

        return orderDetailService.saveAllOrderDetail(orderDetailList);


    }



}

