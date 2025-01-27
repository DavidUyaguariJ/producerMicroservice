package com.udla.producer.message.services;

import org.springframework.stereotype.Service;

import com.udla.models.MessageModel;
import com.udla.producer.publisher.Publisher;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final Publisher publisher;

    public MessageModel sendFanoutMessage(MessageModel messageModel){
        this.publisher.sendFanoutMessage(messageModel);
        return messageModel ;
    }

    public  MessageModel sendTopicMessage(MessageModel messageModel, String routingkey){
        this.publisher.sendTopicMessage(messageModel, routingkey);
        return messageModel;
    }
}