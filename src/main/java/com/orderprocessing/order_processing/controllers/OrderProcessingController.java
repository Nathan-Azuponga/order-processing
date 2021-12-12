package com.orderprocessing.order_processing.controllers;

import com.orderprocessing.order_processing.Services.OrderProcessingService;
import com.orderprocessing.order_processing.dto.OrderDto;
import com.orderprocessing.order_processing.queues.MessagePublisher;
import com.orderprocessing.order_processing.requests.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class OrderProcessingController{
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    MessagePublisher messagePublisher;

    @Autowired
    OrderProcessingService orderProcessingService;

    @PostMapping("/created") //
    public void createOrder(@RequestBody OrderRequest orderRequest) {

        orderProcessingService.create(orderRequest);

    }

    @PostMapping("/updated")
    public OrderDto update(@RequestBody OrderDto dto) {

       return orderProcessingService.update(dto);
    }


}