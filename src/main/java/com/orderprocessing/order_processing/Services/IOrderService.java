package com.orderprocessing.order_processing.Services;


import com.orderprocessing.order_processing.dto.OrderDto;
import org.springframework.stereotype.Component;
import com.orderprocessing.order_processing.requests.OrderRequest;

import java.util.List;

@Component
public interface IOrderService {

    OrderDto create(OrderRequest orderRequest);

    OrderDto update(String id, OrderDto dto);

    OrderDto getOrder(String id);

    boolean cancel(String id);

    List<OrderDto> getOrders();

}