package com.orderprocessing.order_processing.controllers;

import com.orderprocessing.order_processing.Services.OrderProcessingService;
import com.orderprocessing.order_processing.dto.OrderRequestDTO;
import com.orderprocessing.order_processing.queues.MessagePublisher;
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

    @PostMapping("/create")
    public void createOrder(@RequestBody OrderRequestDTO orderRequest) {
        orderProcessingService.create(orderRequest);
    }

    @PostMapping("/update")
    public void update(@RequestBody OrderRequestDTO dto) {
       orderProcessingService.update(dto);
    }


}