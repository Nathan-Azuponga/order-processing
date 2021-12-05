package com.orderprocessing.order_processing.Services.impl;


import com.orderprocessing.order_processing.Services.IOrderService;
import com.orderprocessing.order_processing.dto.OrderDto;
import com.orderprocessing.order_processing.entities.Order;
import com.orderprocessing.order_processing.enums.Status;
import com.orderprocessing.order_processing.exceptions.OrderNotFoundException;
import com.orderprocessing.order_processing.exceptions.UpdateOrderException;
import com.orderprocessing.order_processing.repositories.OrderRepository;
import com.orderprocessing.order_processing.requests.OrderRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
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
    public OrderDto createOrder(OrderRequest orderRequest, String orderId) {
        Order order = new Order();

        order.setSide(orderRequest.getSide());
        order.setQuantity(orderRequest.getQuantity());
        order.setPrice(orderRequest.getPrice());
        order.setProduct(orderRequest.getProduct());
        order.setStatus(Status.PENDING);
        order.setId(orderId);

        Order savedOrder = orderRepository.save(order);
        return OrderDto.fromModel(savedOrder);
    }

    @Override
    public OrderDto getOrder(String id) {
        return null;
    }

    @Override
    public OrderDto updateOrder(String id, OrderDto dto) {
        Order order = orderRepository.findById(id).orElseThrow(()->new OrderNotFoundException("Order with " + id + "not found" ));
        if(order.getStatus() != Status.PENDING){
            throw new UpdateOrderException("Order can not be updated");
        }

        order.setQuantity(dto.getQuantity());
        order.setPrice(dto.getPrice());

        OrderRequest orderRequest = new OrderRequest();

        orderRequest.setSide(order.getSide());
        orderRequest.setPrice(order.getPrice());
        orderRequest.setProduct(order.getProduct());
        orderRequest.setQuantity(order.getQuantity());

        HttpEntity<OrderRequest> request = new HttpEntity<>(orderRequest); //wrapping our body into HttpEntity

        String EXCHANGE_URL = "https://exchange.matraining.com";
        String API_KEY = "a7849689-214b-4ec6-860d-b32603e76859";
        Boolean oid = Optional.ofNullable(restTemplate
                        .exchange(EXCHANGE_URL + "/" + API_KEY + " /order/" +order
                        .getId(), HttpMethod.PUT, request, Boolean.class)
                        .getBody())
                .orElse(false);

        if(oid){
            order = orderRepository.save(order);
        }
        return OrderDto.fromModel(order);
    }


    @Override
    public boolean deleteOrder(String id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isEmpty()) {
            return false;
        }
        orderRepository.deleteById(id);
        return true;
    }

    @Override
    public List<OrderDto> getOrders() {
        return orderRepository.findAll()
                .stream().map(OrderDto::fromModel)
                .collect(Collectors.toList());
    }
}
