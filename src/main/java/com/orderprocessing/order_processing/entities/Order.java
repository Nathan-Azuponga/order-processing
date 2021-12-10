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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
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

}
