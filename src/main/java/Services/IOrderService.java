package Services;


import dto.OrderDto;
import org.springframework.stereotype.Component;
import requests.OrderRequest;

import java.util.List;

@Component
public interface IOrderService {

    OrderDto createOrder(OrderRequest orderRequest, String orderId);

    OrderDto updateOrder(String id, OrderDto dto);

    OrderDto getOrder(String id);

    boolean deleteOrder(String id);

    List<OrderDto> getOrders();

}