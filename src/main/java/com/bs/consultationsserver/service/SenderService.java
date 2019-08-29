package com.bs.consultationsserver.service;

import com.bs.consultationsserver.model.RabbitMqMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public class SenderService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(String queue, byte[] message) {
        rabbitTemplate.convertAndSend(queue, message);
    }

    public void send(String queue, RabbitMqMessage rabbitMqMessage) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            // Convert RabbitMqMessage object to a json string and send it as a byte array
            String jsonPayload = mapper.writeValueAsString(rabbitMqMessage);
            System.out.println("SENDING MESSAGE: " + jsonPayload);
            rabbitTemplate.convertAndSend(queue, jsonPayload.getBytes());
        } catch (JsonProcessingException | AmqpException ex) {
            Logger.getLogger(SenderService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
