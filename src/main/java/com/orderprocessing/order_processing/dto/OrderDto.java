package com.orderprocessing.order_processing.dto;

import com.orderprocessing.order_processing.entities.Order;
import com.orderprocessing.order_processing.entities.Portfolio;
import com.orderprocessing.order_processing.enums.Side;
import com.orderprocessing.order_processing.enums.Status;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private String id;

    private String product;

    private int quantity;

    private double price;

    private Side side;

    @Enumerated(EnumType.STRING)
    private Status status;

    public static OrderDto fromModel(Order order) {
        OrderDto orderDto = new OrderDto();

        orderDto.setId(order.getId());
        orderDto.setPrice(order.getPrice());
        orderDto.setQuantity(order.getQuantity());
        orderDto.setProduct(order.getProduct());
        orderDto.setSide(order.getSide());

        return orderDto;
    }


}