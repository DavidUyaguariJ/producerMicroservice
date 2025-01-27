package com.udla.producer.publisher.configuration;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PublisherConfiguration {
    public static String EXCHANGE_TOPIC = "notification.purchases";
    public static String EXCHANGE_FANOUT = "notification.sales";
    public static String ROUTING_KEY_SALES = "share.*.sales";
    public static String ROUTING_KEY_PURCHASES = "share.*.purchases";
    public static String QUEUE_SALES = "sales.queue";
    public static String QUEUE_PURCHASES = "purchases.queue";

    /**
     * Configure the topic exchange
     */
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(EXCHANGE_TOPIC);
    }

    /**
     * Configure the fanout exchange
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(EXCHANGE_FANOUT);
    }

    @Bean
    public Queue salesQueue() {
        return new Queue(QUEUE_SALES, true);
    }

    @Bean
    public Queue purchasesQueue() {
        return new Queue(QUEUE_PURCHASES, true);
    }

    /**
     * Configure the binding with fanout exchange
     */
    @Bean
    public Binding bindingSalesQueueFanout(FanoutExchange fanoutExchange, Queue salesQueue) {
        return BindingBuilder.bind(salesQueue).to(fanoutExchange);
    }

    @Bean
    public Binding bindingPurchasesQueueFanout(FanoutExchange fanoutExchange, Queue purchasesQueue) {
        return BindingBuilder.bind(purchasesQueue).to(fanoutExchange);
    }

    /**
     * Configure the binding with topic exchange
     */
    @Bean
    public Binding bindingSalesQueueTopic(TopicExchange topicExchange, Queue salesQueue) {
        return BindingBuilder.bind(salesQueue).to(topicExchange).with(ROUTING_KEY_SALES);
    }

    @Bean
    public Binding bindingPurchasesQueueTopic(TopicExchange topicExchange, Queue purchasesQueue) {
        return BindingBuilder.bind(purchasesQueue).to(topicExchange).with(ROUTING_KEY_PURCHASES);
    }

    /**
     * Configure to convert the object to JSON
     */
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connection) {
        final var template = new RabbitTemplate(connection);
        template.setMessageConverter(messageConverter());
        return template;
    }
}