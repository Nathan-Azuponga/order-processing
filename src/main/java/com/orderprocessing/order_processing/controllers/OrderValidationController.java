package com.orderprocessing.order_processing.controllers;

import com.orderprocessing.order_processing.dto.OrderDto;
import com.orderprocessing.order_processing.enums.Side;
import com.orderprocessing.order_processing.enums.Status;
import com.orderprocessing.order_processing.requests.OrderRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderValidationController {
    @PostMapping("order/validate/create")
    public ResponseEntity<Boolean> validateOrder(@RequestBody OrderRequest orderRequest) {

        // Get data from portfolio and client
        double clientBalance = 340.50;
        int clientQuantity = 300;
        String clientProduct = "AAPL";

        if ((orderRequest.getSide() == Side.BUY) && ((orderRequest.getQuantity() * orderRequest.getPrice()) > clientBalance)) {
            System.out.println("Invalid Buy order!!! Respond to the order");

            return new ResponseEntity<>(false, HttpStatus.OK);

        } else if (orderRequest.getSide() == Side.SELL && (orderRequest.getQuantity() > clientQuantity || !clientProduct.equals(orderRequest.getProduct()))) {
            System.out.println("Invalid Sell order!!! Respond to the order");

            return new ResponseEntity<>(false, HttpStatus.OK);

        }
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PostMapping("/validate/update")
    public ResponseEntity<Boolean> validateUpdateOrder(OrderDto dto) {
        return new ResponseEntity<>(dto.getStatus().equals(Status.PENDING), HttpStatus.OK);
    }
}
