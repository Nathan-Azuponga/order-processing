package com.orderprocessing.order_processing.controllers;

import com.orderprocessing.order_processing.Services.IOrderService;
import com.orderprocessing.order_processing.Services.OrderProcessingService;
import com.orderprocessing.order_processing.dto.OrderDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import com.orderprocessing.order_processing.requests.OrderRequest;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final IOrderService orderService;

    private final RestTemplate restTemplate;

    private final OrderProcessingService orderProcessingService;

    public OrderController(IOrderService orderService, RestTemplate restTemplate, OrderProcessingService orderProcessingService) {
        this.orderService = orderService;
        this.restTemplate = restTemplate;
        this.orderProcessingService = orderProcessingService;
    }

    @PostMapping("/create")
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderRequest orderRequest) {

        OrderDto orderDto = orderService.create(orderRequest);
        return new ResponseEntity<>(orderDto, orderDto == null ? HttpStatus.BAD_REQUEST : HttpStatus.OK);
    }

    @PostMapping(path = "/update/{id}")
    public ResponseEntity<OrderDto> updateOrder(@PathVariable String id, @RequestBody OrderDto dto) {

        OrderDto orderDto = orderService.update(id, dto);
        return new ResponseEntity<>(orderDto, orderDto == null ? HttpStatus.BAD_REQUEST : HttpStatus.OK);
    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity<Void> deletedOrder(@PathVariable String id) {

        boolean cancelOrder = orderService.cancel(id);
        return new ResponseEntity<>(null,cancelOrder?HttpStatus.NO_CONTENT:HttpStatus.INTERNAL_SERVER_ERROR);
    }
}