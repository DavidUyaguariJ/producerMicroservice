package com.udla.producer.publisher;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.udla.models.MessageModel;
import com.udla.producer.publisher.configuration.PublisherConfiguration;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class Publisher {

    private final RabbitTemplate rabbitTemplate;

    /**
     * Sends a message to the topic exchange
     *
     * @param messageModel the object to send
     * @param routingKey the routing key to use
     */
    public void sendTopicMessage(MessageModel messageModel, String routingKey) {
        this.rabbitTemplate.convertAndSend(PublisherConfiguration.EXCHANGE_TOPIC, routingKey, messageModel);
    }

    /**
     * Sends a message to the fanout exchange
     *
     * @param messageModel the object to send
     */
    public void sendFanoutMessage(MessageModel messageModel) {
        this.rabbitTemplate.convertAndSend(PublisherConfiguration.EXCHANGE_FANOUT, "", messageModel);
    }
}
