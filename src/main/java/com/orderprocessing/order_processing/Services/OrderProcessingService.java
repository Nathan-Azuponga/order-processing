package com.orderprocessing.order_processing.Services;

import com.orderprocessing.order_processing.dto.OrderDto;
import com.orderprocessing.order_processing.entities.Order;
import com.orderprocessing.order_processing.enums.Status;
import com.orderprocessing.order_processing.repositories.OrderRepository;
import com.orderprocessing.order_processing.requests.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
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
    public void createOrder2(@RequestBody OrderRequest orderRequest) {
        ResponseEntity<String> oid = restTemplate.postForEntity(EXCHANGE_URL + "/" + API_KEY + " /order", orderRequest, String.class);
        String  orderId  = oid.getBody().replaceAll("\"", "");

        Order order = new Order();

        order.setSide(orderRequest.getSide());
        order.setQuantity(orderRequest.getQuantity());
        order.setPrice(orderRequest.getPrice());
        order.setProduct(orderRequest.getProduct());
        order.setStatus(Status.PENDING);
        order.setId(orderId);

        // Sending to reporting/logging system
        restTemplate.postForEntity("https://smartstakereportingservice.herokuapp.com/logorder",
                order,
                String.class);
    }


    //Work on how to get data to it
    @PostMapping("/updated")
    public void update(OrderDto dto, String id){

        Order order = new Order();

        order.setQuantity(dto.getQuantity());
        order.setPrice(dto.getPrice());

        OrderRequest orderRequest = new OrderRequest();

        orderRequest.setSide(order.getSide());
        orderRequest.setPrice(order.getPrice());
        orderRequest.setProduct(order.getProduct());
        orderRequest.setQuantity(order.getQuantity());

        System.out.println(orderRequest.getPrice());

        HttpEntity<OrderRequest> request = new HttpEntity<>(orderRequest); //wrapping our body into HttpEntity

        ResponseEntity<Boolean> oid = restTemplate
                        .exchange(EXCHANGE_URL + "/" + API_KEY + " /order/" + order
                                .getId(), HttpMethod.PUT, request, Boolean.class);


        if (Boolean.TRUE.equals(oid.getBody())) {
            //System.out.println("Send it to the logging/Reporting service");
            restTemplate.postForEntity("https://smartstakereportingservice.herokuapp.com/logorder",orderRequest,String.class);
        }

    }




}
