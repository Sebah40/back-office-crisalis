package com.orange.Crisalis.service.interfaces;

import com.orange.Crisalis.dto.RequestBodyCreateOrderDTO;
import com.orange.Crisalis.model.dto.OrderDTO;
import com.orange.Crisalis.model.dto.OrderWithCalculationEngineDTO;
import com.orange.Crisalis.model.dto.filters.OrderFilter;


import java.util.List;
import java.util.Optional;

public interface IOrderService {

    Optional<OrderDTO> getOrder(Long id);
    OrderWithCalculationEngineDTO getOrderWithCalculation(Long id);
    void createOrder(RequestBodyCreateOrderDTO orderCreateBody);
    List<OrderDTO> getOrders();

    void cancelOrder(Long id);

    void validateOrder(Long id);

    void editOrder(OrderDTO order);

    List<OrderWithCalculationEngineDTO> getOrdersWithSubTotal();

    List<OrderDTO> filterOrderList(OrderFilter orderFilter);

    List<OrderDTO> getAllByClientId(Long id);

}
