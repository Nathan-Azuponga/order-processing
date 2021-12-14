package com.orderprocessing.order_processing.Services;

import com.orderprocessing.order_processing.dto.OrderEntityDTO;
import com.orderprocessing.order_processing.dto.OrderRequestDTO;
import com.orderprocessing.order_processing.enums.Status;
import com.orderprocessing.order_processing.exceptions.UpdateOrderException;
import com.orderprocessing.order_processing.queues.MConfig;
import com.orderprocessing.order_processing.queues.MessagePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;


@Service
public class OrderProcessingService {

    private final String EXCHANGE_URL = "https://exchange.matraining.com";
    private final String EXCHANGE_URL2 = "https://exchange2.matraining.com";
    private final String API_KEY = "a7849689-214b-4ec6-860d-b32603e76859";
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    MessagePublisher messagePublisher;

    public void create(OrderRequestDTO orderRequest) {
        ResponseEntity<String> oid = restTemplate.postForEntity(EXCHANGE_URL2 + "/" + API_KEY + " /order", orderRequest, String.class);
        String orderId = Objects.requireNonNull(oid.getBody()).replaceAll("\"", "");

        OrderEntityDTO order = new OrderEntityDTO(
                orderId,
                orderRequest.getProduct(),
                orderRequest.getQuantity(),
                orderRequest.getPrice(),
                orderRequest.getSide().name(),
                Status.PENDING.name(),
                orderRequest.getPortfolioId()
        );
        messagePublisher.publishMessage(order);
//        template.convertAndSend(MConfig.EXCHANGE, MConfig.ROUTING_KEY, order);
    }


    public void update(OrderRequestDTO orderRequest) {

//        OrderRequest orderRequest = new OrderRequest();
//
//        orderRequest.setSide(dto.getSide());
//        orderRequest.setPrice(dto.getPrice());
//        orderRequest.setProduct(dto.getProduct());
//        orderRequest.setQuantity(dto.getQuantity());

        HttpEntity<OrderRequestDTO> request = new HttpEntity<>(orderRequest); //wrapping our body into HttpEntity

        ResponseEntity<Boolean> oid = restTemplate
                .exchange(EXCHANGE_URL + "/" + API_KEY + " /order/" + orderRequest
                        .getId(), HttpMethod.PUT, request, Boolean.class);
        if (oid.getStatusCode().is5xxServerError()) {
            throw new UpdateOrderException("Order has already been fulfilled");
        }

        if (Boolean.TRUE.equals(oid.getBody())) {

            //System.out.println("Send it to the logging/Reporting service");
            restTemplate.postForEntity("https://smartstakereportingservice.herokuapp.com/logorder", orderRequest, String.class);
        }
    }
}
