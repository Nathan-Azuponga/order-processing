package com.orderprocessing.order_processing.responses;

import java.time.LocalDateTime;

public class ErrorResponse {

    private int status;
    private String message;
    private LocalDateTime timestamp;

    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
        timestamp = LocalDateTime.now();
    }
}
