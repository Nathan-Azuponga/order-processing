package com.orderprocessing.order_processing.Services;

import com.orderprocessing.order_processing.dto.OrderDto;
import com.orderprocessing.order_processing.entities.Order;
import com.orderprocessing.order_processing.enums.Status;
import com.orderprocessing.order_processing.repositories.OrderRepository;
import com.orderprocessing.order_processing.requests.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@RestController
public class OrderProcessingService {
    @Autowired
    RestTemplate restTemplate;

     @Autowired
    private OrderRepository orderRepository;

    private final String EXCHANGE_URL = "https://exchange.matraining.com";
    private final String API_KEY = "a7849689-214b-4ec6-860d-b32603e76859";


    @PostMapping( "/created") //
    public OrderDto createOrder2(@RequestBody OrderRequest orderRequest) {
        ResponseEntity<String> oid = restTemplate.postForEntity(EXCHANGE_URL + "/" + API_KEY + " /order", orderRequest, String.class);
        String  orderId  = oid.getBody().replaceAll("\"", "");

        Order order = new Order();

        order.setSide(orderRequest.getSide());
        order.setQuantity(orderRequest.getQuantity());
        order.setPrice(orderRequest.getPrice());
        order.setProduct(orderRequest.getProduct());
        order.setStatus(Status.PENDING);
        order.setId(orderId);

        Order savedOrder = orderRepository.save(order);
        return OrderDto.fromModel(savedOrder);

        // Send to reporting/logging system


    }

    @PutMapping("/update")
    public OrderDto update(OrderDto dto){
        Order order = new Order();

        order.setQuantity(dto.getQuantity());
        order.setPrice(dto.getPrice());

        OrderRequest orderRequest = new OrderRequest();

        orderRequest.setSide(order.getSide());
        orderRequest.setPrice(order.getPrice());
        orderRequest.setProduct(order.getProduct());
        orderRequest.setQuantity(order.getQuantity());

        HttpEntity<OrderRequest> request = new HttpEntity<>(orderRequest); //wrapping our body into HttpEntity

        Boolean oid = Optional.ofNullable(restTemplate
                        .exchange(EXCHANGE_URL + "/" + API_KEY + " /order/" + order
                                .getId(), HttpMethod.PUT, request, Boolean.class)
                        .getBody())
                .orElse(false);

        if (oid) {
            System.out.println("Send it to the logging/Reporting service");

            //order = orderRepository.save(order);
        }
        return OrderDto.fromModel(order);
    }


}
