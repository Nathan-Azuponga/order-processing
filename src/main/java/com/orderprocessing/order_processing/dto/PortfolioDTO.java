package com.orderprocessing.order_processing.dto;


import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioDTO {

    private Long id;

    private String name;

    private String description;

    private double balance;

    private long clientId;

    private Set<PositionDTO> positions = new HashSet<>();
}
