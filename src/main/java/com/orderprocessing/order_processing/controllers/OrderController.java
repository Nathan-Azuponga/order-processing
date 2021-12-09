package com.orderprocessing.order_processing.controllers;

import com.orderprocessing.order_processing.Services.IOrderService;
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

    public OrderController(IOrderService orderService, RestTemplate restTemplate) {
        this.orderService = orderService;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/orders")
    @ResponseStatus(code = HttpStatus.OK)
    public List<OrderDto> getOrders(){
        return orderService.getOrders();
    }

    @PostMapping( "/create")
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderRequest orderRequest) {
        HttpEntity<OrderRequest> request = new HttpEntity<>(orderRequest);

        ResponseEntity<Boolean> isValidOrder = restTemplate.postForEntity("http://localhost:8080/order/validate/create ",
                request,
                Boolean.class);
        Boolean status = Optional.ofNullable(isValidOrder.getBody()).orElse(false);

        if(isValidOrder.getStatusCode()==HttpStatus.OK && status){
            ResponseEntity<OrderDto> dto = restTemplate.postForEntity("http://localhost:8080/created",
                    orderRequest,
                    OrderDto.class);

            OrderDto orderDto = Optional.ofNullable(dto.getBody()).orElse(null);
            if(dto.getStatusCode()==HttpStatus.OK){
                return new ResponseEntity<>(orderDto,HttpStatus.CREATED);
            }
        } return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
    }

    @PostMapping(path = "/update/{id}")
    public ResponseEntity<OrderDto> updateOrder(@PathVariable String id, @RequestBody OrderDto dto){
        ResponseEntity<Boolean> isUpdateOrder = restTemplate.postForEntity("http://localhost:8080/validate/update",
                dto,
                Boolean.class);
        if(Boolean.TRUE.equals(isUpdateOrder.getBody())){
            dto.setId(id);
            restTemplate.postForEntity("http://localhost:8080/updated",
                    dto,
                    String.class);
        }

        return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/cancel/{id}")
    public String deletedOrder(@PathVariable("id") String id){

        String EXCHANGE_URL = "https://exchange.matraining.com";

        String API_KEY = "a7849689-214b-4ec6-860d-b32603e76859";
        ResponseEntity<Boolean> isCancelled = restTemplate.execute(
                EXCHANGE_URL + "/" + API_KEY +"/order/" + id,
                HttpMethod.DELETE,
              null,
                restTemplate.responseEntityExtractor(Boolean.class)
        );
        assert isCancelled != null;
        Boolean statusCancel = Optional.ofNullable(isCancelled.getBody()).orElse(false);
        if(statusCancel){
            restTemplate.postForEntity("https://smartstakereportingservice.herokuapp.com/order/delete/{id}",
                    id,
                    String.class);
        }
        return "Successfully deleted";
    }
}