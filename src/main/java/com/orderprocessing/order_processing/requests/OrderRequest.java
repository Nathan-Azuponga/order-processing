package com.orderprocessing.order_processing.requests;


import com.orderprocessing.order_processing.enums.Side;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {


    private String product;


    private int quantity;


    private double price;


    @Enumerated(EnumType.STRING)
    private Side side;


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

    @Override
    public String toString() {
        return "OrderRequest{" +
                "product='" + product + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", side=" + side +
                '}';
    }
}
