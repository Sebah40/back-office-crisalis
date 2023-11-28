package com.orange.Crisalis.controller;


import com.orange.Crisalis.dto.FilteredReportDTO;
import com.orange.Crisalis.dto.RequestBodyCreateOrderDTO;

import com.orange.Crisalis.exceptions.ErrorMessage;
import com.orange.Crisalis.exceptions.custom.OrderNotFoundException;
import com.orange.Crisalis.model.dto.OrderDTO;
import com.orange.Crisalis.model.dto.OrderWithCalculationEngineDTO;
import com.orange.Crisalis.model.dto.filters.OrderFilter;
import com.orange.Crisalis.security.Controller.Message;
import com.orange.Crisalis.service.OrderService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.ValidationException;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/order")
@CrossOrigin(origins = {"http://localhost:4200", "https://localhost:4200", "*"})
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER' ,'ADMIN')")
    public ResponseEntity<Object> getOrder(@PathVariable("id") Long id) {
        Optional<OrderDTO> order = this.orderService.getOrder(id);
        if(order.isPresent()) {
            return ResponseEntity.ok(order);
        }
        return new ResponseEntity<Object>("Pedido inexistente", HttpStatus.NOT_FOUND);
    }

    @Transactional
    @PostMapping(value = "/create",consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('USER' ,'ADMIN')")
    public ResponseEntity<?> createOrder (@RequestBody RequestBodyCreateOrderDTO orderCreateBody){
        this.orderService.createOrder(orderCreateBody);
        return new ResponseEntity<>(new Message("pedido creado con exito"), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('USER' ,'ADMIN')")
    @GetMapping("/getAll")
    public List<OrderDTO> getAll(){
        return this.orderService.getOrders();
    }

    @PreAuthorize("hasAnyRole('USER' ,'ADMIN')")
    @GetMapping("/filter")
    public List<OrderDTO> filterOrderList(@RequestBody OrderFilter orderFilter) {
        return this.orderService.filterOrderList(orderFilter);
    }


    @PreAuthorize("hasAnyRole('USER' ,'ADMIN')")
    @DeleteMapping("/cancel/{id}")
    public ResponseEntity<?> cancelOrder(@PathVariable("id") Long id){
        this.orderService.cancelOrder(id);
        return new ResponseEntity<>(new Message("El pedido ha sido cancelado"),HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('USER' ,'ADMIN')")
    @PutMapping("/edit")
    public ResponseEntity<?> editOrder(@RequestBody OrderDTO orderToEdit){
        this.orderService.editOrder(orderToEdit);
        return new ResponseEntity<>(new Message("El pedido editado exisotamente"),HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('USER' ,'ADMIN')")
    @PutMapping("/validate/{orderId}")
    public ResponseEntity<?> validateOrder(@PathVariable Long orderId) {
        try {
            orderService.validateOrder(orderId);
            return new ResponseEntity<>(new Message("El pedido ha sido validado exitosamente"), HttpStatus.OK);
        } catch (ValidationException e) {
            ErrorMessage errorMessage = new ErrorMessage(e, "/validate/" + orderId);
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        } catch (OrderNotFoundException e) {
            ErrorMessage errorMessage = new ErrorMessage(e, "/validate/" + orderId);
            return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/filteredReport")
    public ResponseEntity<Set<FilteredReportDTO>> getFilteredReport(
            @RequestParam(name = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam(name = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
            @RequestParam(name = "sellableGood", required = false) String sellableGood,
            @RequestParam(name = "clientID", required = false) Integer clientID) {
        if (startDate == null) {
            startDate = Date.from(Date.from(Instant.parse("1980-01-13T00:00:00Z")).toInstant());
        }

        if (endDate == null) {
            endDate = Date.from(Date.from(Instant.parse("2300-01-13T23:59:59Z")).toInstant());
        }
        Set<FilteredReportDTO> filteredReportDTO = orderService.getDTO(startDate, endDate, sellableGood, clientID);
        if (filteredReportDTO.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(filteredReportDTO);
    }

    @GetMapping("/get")
    @PreAuthorize("hasAnyRole('USER' ,'ADMIN')")
    public List<OrderWithCalculationEngineDTO> getOrders(){
        return this.orderService.getOrdersWithSubTotal();
    }

    @GetMapping("/withcalculation/{id}")
    @PreAuthorize("hasAnyRole('USER' ,'ADMIN')")
    public ResponseEntity<Object> getOrderWithCalculation(@PathVariable("id") Long id) {
        OrderWithCalculationEngineDTO order = this.orderService.getOrderWithCalculation(id);
        System.out.println("ASFDASFQWERQWERQWERQWERQWERQWERQWERQWERQWERQWER");
        if(order != null) {
            return ResponseEntity.ok(order);
        }
        return new ResponseEntity<Object>("Pedido inexistente", HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasAnyRole('USER' ,'ADMIN')")
    @GetMapping("/getAllByClient/{id}")
    public ResponseEntity<List<OrderDTO>> getAllByClientId(@PathVariable("id") Long id){
        return ResponseEntity.ok(this.orderService.getAllByClientId(id));
    }


}


















