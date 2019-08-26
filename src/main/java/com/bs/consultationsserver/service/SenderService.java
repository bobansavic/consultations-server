package com.bs.consultationsserver.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public class SenderService {

  @Autowired
  private RabbitTemplate rabbitTemplate;

  public void send(String queue, byte[] message) {
    rabbitTemplate.convertAndSend(queue, message);
  }
}
