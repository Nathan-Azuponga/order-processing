package com.orderprocessing.order_processing.controllers;

import com.orderprocessing.order_processing.Services.OrderValidationService;
import com.orderprocessing.order_processing.dto.OrderDto;
import com.orderprocessing.order_processing.enums.Side;
import com.orderprocessing.order_processing.enums.Status;
import com.orderprocessing.order_processing.requests.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderValidationController {

    @Autowired
    OrderValidationService orderValidationService;

    @PostMapping("order/validate/create")
    public ResponseEntity<Boolean> validateOrder(@RequestBody OrderRequest orderRequest) {
        return new ResponseEntity<>(orderValidationService.validate(orderRequest), HttpStatus.OK);
    }

    @PostMapping("/validate/update")
    public ResponseEntity<Boolean> validateUpdateOrder(OrderDto dto) {
        return new ResponseEntity<>(dto.getStatus().equals(Status.PENDING), HttpStatus.OK);
    }
}
