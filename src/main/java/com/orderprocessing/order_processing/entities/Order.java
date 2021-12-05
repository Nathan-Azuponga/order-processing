package com.orderprocessing.order_processing.entities;

import com.orderprocessing.order_processing.dto.OrderDto;
import com.orderprocessing.order_processing.enums.Side;
import com.orderprocessing.order_processing.enums.Status;

import javax.persistence.*;

@Entity
@Table(name = "Orders")
public class Order {
    @Id
    private String id;

    private String product;
    private int quantity;
    private Double price;

    @Enumerated(EnumType.STRING)
    private Side side;

    @Enumerated(EnumType.STRING)
    private Status status;

//    @ManyToOne
//    private Portfolio portfolio;

    public String getId() {
        return id;
    }

    public void setId (String id) {
        this.id = id;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Side getSide() {
        return side;
    }

    public void setSide(Side side) {
        this.side = side;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

//    public Portfolio getPortfolio() {
//        return portfolio;
//    }
//
//    public void setPortfolio(Portfolio portfolio) {
//        this.portfolio = portfolio;
//    }

    public static Order fromDto(OrderDto orderDto) {
        Order order = new Order();
        order.setProduct(orderDto.getProduct());
//        order.setPortfolio(orderDto.getPortfolio());
        order.setPrice(orderDto.getPrice());

        return order;
    }
}
