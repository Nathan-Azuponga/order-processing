package com.orderprocessing.order_processing.controllers;

import com.orderprocessing.order_processing.Services.IOrderService;
import com.orderprocessing.order_processing.dto.OrderDto;
import com.orderprocessing.order_processing.entities.ExchangeOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
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

    @PostMapping( "/create2")
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderRequest orderRequest) {
        HttpEntity<OrderRequest> request = new HttpEntity<>(orderRequest);
        System.out.println(orderRequest);
        System.out.println(request);

        ResponseEntity<Boolean> isValidOrder = restTemplate.postForEntity("http://localhost:8080/order/validate/create ", request, Boolean.class);
        Boolean status = Optional.ofNullable(isValidOrder.getBody()).orElse(false);

        if(isValidOrder.getStatusCode()==HttpStatus.OK && status){
            ResponseEntity<OrderDto> dto = restTemplate.postForEntity("http://localhost:8080/created",orderRequest,OrderDto.class);
            OrderDto orderDto = Optional.ofNullable(dto.getBody()).orElse(null);
            if(dto.getStatusCode()==HttpStatus.OK){
                return new ResponseEntity<>(orderDto,HttpStatus.CREATED);
            }
        } return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
    }


    @GetMapping("/getOrder/{id}")
    public ExchangeOrder getOrders(@PathVariable String id){
        ExchangeOrder exchangeOrder = restTemplate.getForObject(String.format("%s/%s/order/%s", EXCHANGE_URL, API_KEY, id), ExchangeOrder.class);
        return exchangeOrder;
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<OrderDto> updateOrder(@PathVariable String id, @RequestBody OrderDto dto){
        ResponseEntity<Boolean> isValidOrder = restTemplate.postForEntity("http://localhost:8080/order/update1", dto, Boolean.class);

        return new ResponseEntity<>(orderService.updateOrder(dto),HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public String deletedOrder(@PathVariable("id") String id){
        restTemplate.delete( EXCHANGE_URL + "/" + API_KEY +"/order/" + id);
        orderService.deleteOrder(id);
        return "Successfully deleted";
    }
}