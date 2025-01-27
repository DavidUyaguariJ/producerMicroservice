package com.udla.producer.message.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.udla.models.MessageModel;
import com.udla.producer.message.services.MessageService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(path="/message")
public class MessageController {

    private final MessageService messageService;

    @PostMapping("/sendFanout")
    public ResponseEntity<MessageModel> sendFanoutMessage(@RequestBody MessageModel message){
        return ResponseEntity.ok(this.messageService.sendFanoutMessage(message));
    }

    @PostMapping("/sendTopic")
    public ResponseEntity<MessageModel> sendTopicMessage(@RequestBody MessageModel message){
        return ResponseEntity.ok(this.messageService.sendTopicMessage(message));
    }






}
