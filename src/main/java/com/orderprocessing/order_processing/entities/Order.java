package com.orderprocessing.order_processing.entities;

import com.orderprocessing.order_processing.enums.Side;
import com.orderprocessing.order_processing.enums.Status;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "Orders")
@Data
@NoArgsConstructor
@ToString
public class Order {
    @Id
    private String id;

    private String product;
    private int quantity;
    private double price;

    @Enumerated(EnumType.STRING)
    private Side side;

    @Enumerated(EnumType.STRING)
    private Status status;
}
