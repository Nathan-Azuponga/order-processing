package com.orderprocessing.order_processing.Services;


import com.orderprocessing.order_processing.dto.OrderDto;
import org.springframework.stereotype.Component;
import com.orderprocessing.order_processing.requests.OrderRequest;

import java.util.List;

@Component
public interface IOrderService {

    OrderDto createOrder(OrderRequest orderRequest, String orderId);

    OrderDto updateOrder(String id, OrderDto dto);

    OrderDto getOrder(String id);

    boolean deleteOrder(String id);

    List<OrderDto> getOrders();

}