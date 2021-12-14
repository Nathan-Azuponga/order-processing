package com.orderprocessing.order_processing.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PositionDTO {

    private Long id;

    private String product;

    private Long quantity;

    private Long portfolioId;
}
