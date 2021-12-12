package com.orderprocessing.order_processing.Services;

import com.orderprocessing.order_processing.enums.Side;
import com.orderprocessing.order_processing.requests.OrderRequest;
import org.springframework.stereotype.Service;

@Service
public class OrderValidationService {
    public Boolean validate(OrderRequest orderRequest){

        double clientBalance = 340.50;
        int clientQuantity = 300;
        String clientProduct = "AAPL";

        if ((orderRequest.getSide() == Side.BUY) && ((orderRequest.getQuantity() * orderRequest.getPrice()) > clientBalance)) {
            System.out.println("Invalid Buy order!!! Respond to the order");

            return false;

        } else if (orderRequest.getSide() == Side.SELL && (orderRequest.getQuantity() > clientQuantity || !clientProduct.equals(orderRequest.getProduct()))) {
            System.out.println("Invalid Sell order!!! Respond to the order");

            return false;

        }
        return true;
    }

}
