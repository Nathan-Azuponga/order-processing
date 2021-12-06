package com.orderprocessing.order_processing.controllers;

import com.orderprocessing.order_processing.Services.IOrderService;
import com.orderprocessing.order_processing.dto.OrderDto;
import com.orderprocessing.order_processing.entities.ExchangeOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import com.orderprocessing.order_processing.requests.OrderRequest;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @Autowired
    private RestTemplate restTemplate;

    private final String EXCHANGE_URL = "https://exchange.matraining.com";
    private final String API_KEY = "a7849689-214b-4ec6-860d-b32603e76859";

    @GetMapping("/orders")
    @ResponseStatus(code = HttpStatus.OK)
    public List<OrderDto> getOrders(){
        return orderService.getOrders();
    }

    @PostMapping( "/create")
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderRequest orderRequest) {
        ResponseEntity<Boolean> isValidOrder = restTemplate.postForEntity("http://localhost:8080/order/validate", orderRequest, Boolean.class);

        String orderId = ""; // oid.getBody().replaceAll("\"", "");
        OrderDto orderDto = orderService.createOrder(orderRequest, orderId);
        return new ResponseEntity<>(orderDto, HttpStatus.CREATED);
    }

    @PostMapping( "/create2") //old one
    public ResponseEntity<OrderDto> createOrder2(@RequestBody OrderRequest orderRequest) {
        ResponseEntity<String> oid = restTemplate.postForEntity(EXCHANGE_URL + "/" + API_KEY + " /order", orderRequest, String.class);
        String orderId = oid.getBody().replaceAll("\"", "");
        OrderDto orderDto = orderService.createOrder(orderRequest, orderId);
        return new ResponseEntity<>(orderDto, HttpStatus.CREATED);
    }

    @GetMapping("/getOrder/{id}")
    public ExchangeOrder getOrders(@PathVariable String id){
        ExchangeOrder exchangeOrder = restTemplate.getForObject(String.format("%s/%s/order/%s", EXCHANGE_URL, API_KEY, id), ExchangeOrder.class);
        return exchangeOrder;
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<OrderDto> updateOrder(@PathVariable String id, @RequestBody OrderDto dto){
        return new ResponseEntity<>(orderService.updateOrder(id,dto),HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public String deletedOrder(@PathVariable("id") String id){
        restTemplate.delete( EXCHANGE_URL + "/" + API_KEY +"/order/" + id);
        orderService.deleteOrder(id);
        return "Successfully deleted";
    }
}