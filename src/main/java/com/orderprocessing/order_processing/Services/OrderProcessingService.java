package com.orderprocessing.order_processing.Services;

import com.orderprocessing.order_processing.dto.OrderDto;
import com.orderprocessing.order_processing.requests.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

public class OrderProcessingService {
    @Autowired
    RestTemplate restTemplate;



    private final String EXCHANGE_URL = "https://exchange.matraining.com";
    private final String API_KEY = "a7849689-214b-4ec6-860d-b32603e76859";


    @PostMapping( "/create2") //old one
    public void createOrder2(@RequestBody OrderRequest orderRequest) {
        ResponseEntity<String> oid = restTemplate.postForEntity(EXCHANGE_URL + "/" + API_KEY + " /order", orderRequest, String.class);
        String  orderId  = oid.getBody().replaceAll("\"", "");
//        OrderDto orderDto = orderService.createOrder(orderRequest, orderId);

        // Send to Reporting/Logging service

    }
}
