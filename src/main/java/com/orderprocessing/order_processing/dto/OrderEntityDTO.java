package com.orderprocessing.order_processing.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntityDTO {

    private String id;

    private String product;

    private int quantity;

    private double price;

    private String side;

    private String status;

    private Long portfolioId;
}
