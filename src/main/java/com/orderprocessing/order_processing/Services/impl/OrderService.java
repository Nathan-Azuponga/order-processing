package com.orderprocessing.order_processing.Services.impl;


import com.orderprocessing.order_processing.Services.IOrderService;
import com.orderprocessing.order_processing.dto.OrderDto;
import com.orderprocessing.order_processing.entities.Order;
import com.orderprocessing.order_processing.exceptions.OrderNotFoundException;
import com.orderprocessing.order_processing.repositories.OrderRepository;
import com.orderprocessing.order_processing.requests.OrderRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService implements IOrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestTemplate restTemplate;


    @Override
    public OrderDto create(OrderRequest orderRequest) {
        HttpEntity<OrderRequest> request = new HttpEntity<>(orderRequest);

        ResponseEntity<Boolean> isValidOrder = restTemplate.postForEntity("http://localhost:8080/order/validate/create ",
                request,
                Boolean.class);
        Boolean status = Optional.ofNullable(isValidOrder.getBody()).orElse(false);

        if (isValidOrder.getStatusCode() == HttpStatus.OK && status) {
            ResponseEntity<OrderDto> dto = restTemplate.postForEntity("http://localhost:8080/created",
                    orderRequest,
                    OrderDto.class);

            OrderDto orderDto = Optional.ofNullable(dto.getBody()).orElse(null);
            if (dto.getStatusCode() == HttpStatus.OK) {
                return orderDto;
            }
        }
        return null;
    }

    @Override
    public OrderDto getOrder(String id) {
        return null;
    }

    @Override
    public OrderDto update(String id, OrderDto dto) {

        ResponseEntity<Boolean> isUpdateOrder = restTemplate.postForEntity("http://localhost:8080/validate/update",
                dto,
                Boolean.class);
        if (Boolean.TRUE.equals(isUpdateOrder.getBody())) {
            dto.setId(id);

            //orderProcessingService.update(dto);
            ResponseEntity<OrderDto> orderdto = restTemplate.postForEntity("http://localhost:8080/updated",
                    dto,
                    OrderDto.class);
            return orderdto.getBody();
        }
        return null;
    }


    @Override
    public boolean cancel(String id) {

        String EXCHANGE_URL = "https://exchange.matraining.com";
        String API_KEY = "a7849689-214b-4ec6-860d-b32603e76859";

        ResponseEntity<Boolean> isCancelled = restTemplate.execute(
                EXCHANGE_URL + "/" + API_KEY + "/order/" + id,
                HttpMethod.DELETE,
                null,
                restTemplate.responseEntityExtractor(Boolean.class));

        assert isCancelled != null;
        Boolean statusCancel = Optional.ofNullable(isCancelled.getBody()).orElse(false);

        if (statusCancel) {
            restTemplate.put("https://smartstakereportingservice.herokuapp.com/order/delete/" + id,
                    id,
                    String.class);
            return true;
        }
        return false;
    }

    @Override
    public List<OrderDto> getOrders() {
        return orderRepository.findAll()
                .stream().map(OrderDto::fromModel)
                .collect(Collectors.toList());
    }
}
