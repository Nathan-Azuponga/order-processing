package com.orderprocessing.order_processing.queues;


import com.orderprocessing.order_processing.dto.OrderEntityDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessagePublisher {

    @Autowired
    private RabbitTemplate template;

    //@PostMapping("/publish")
    public String publishMessage(@RequestBody OrderEntityDTO order) {
        template.convertAndSend(MConfig.EXCHANGE, MConfig.ROUTING_KEY, order);
        return "Message Published";
    }
}
