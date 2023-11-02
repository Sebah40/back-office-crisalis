package com.orange.Crisalis.controller;


import com.orange.Crisalis.dto.RequestBodyCreateOrderDTO;
import com.orange.Crisalis.model.OrderEntity;
import com.orange.Crisalis.model.dto.OrderDTO;
import com.orange.Crisalis.security.Controller.Message;
import com.orange.Crisalis.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Transactional
    @PostMapping(value = "/create",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createOrder (@RequestBody RequestBodyCreateOrderDTO orderCreateBody){
        this.orderService.createOrder(orderCreateBody);
        return new ResponseEntity<>(new Message("pedido creado con exito"), HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public List<OrderDTO> getAll(){
        return this.orderService.getOrders();

    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity<?> cancelOrder(@PathVariable("id") Long id){
        this.orderService.cancelOrder(id);
        return new ResponseEntity<>(new Message("El pedido ha sido cancelado"),HttpStatus.OK);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editOrder(@RequestBody RequestBodyCreateOrderDTO orderToEdit,@PathVariable("id") Long id){
        this.orderService.editOrder(orderToEdit,id);
        return new ResponseEntity<>(new Message("El pedido editado exisotamente"),HttpStatus.OK);
    }
}


















