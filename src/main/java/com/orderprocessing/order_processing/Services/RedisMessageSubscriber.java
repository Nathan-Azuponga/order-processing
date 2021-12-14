package com.orderprocessing.order_processing.Services;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.orderprocessing.order_processing.dto.ExchangeData;
import com.orderprocessing.order_processing.dto.MarketDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class RedisMessageSubscriber implements MessageListener {

    ObjectMapper objectMapper = new ObjectMapper();

    public static List<String> messageList = new ArrayList<String>();

    public void onMessage(Message message, byte[] pattern) {
        try {
            MarketDto[] marketDtos = objectMapper.readValue(
                    new String(message.getBody()),
                    MarketDto[].class
            );
            ExchangeData.exchangeData = new ArrayList<>(Arrays.asList(marketDtos));
            log.info("Data published from Smart Stake Market data: {}", ExchangeData.exchangeData);
        } catch (IOException e){
            log.error("Could not publish", e);
        }
    }
}

