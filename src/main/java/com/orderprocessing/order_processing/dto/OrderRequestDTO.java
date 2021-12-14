package com.orderprocessing.order_processing.dto;

import com.orderprocessing.order_processing.enums.Side;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDTO {

    private String id;

    private String product;

    private int quantity;

    private double price;

    private Side side;

    private Long portfolioId;
}
