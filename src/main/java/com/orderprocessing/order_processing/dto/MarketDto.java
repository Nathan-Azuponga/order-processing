package com.orderprocessing.order_processing.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class MarketDto {

    @JsonProperty
    private String LAST_TRADED_PRICE;
    @JsonProperty
    private String SELL_LIMIT;
    @JsonProperty
    private String BID_PRICE;
    @JsonProperty
    private String ASK_PRICE;
    @JsonProperty
    private String BUY_LIMIT;
    @JsonProperty
    private String TICKER;
    @JsonProperty
    private String MAX_PRICE_SHIFT;

}

