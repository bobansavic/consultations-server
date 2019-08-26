package com.bs.consultationsserver.service;

import com.bs.consultationsserver.model.RabbitMqMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

@RabbitListener(queues = "server")
public class ListenerService {
  private static final String ACTION_CODE_0 = "ACTION_CODE_0";
  private static final String ACTION_CODE_1 = "ACTION_CODE_1";
  private final String QUEUE_1 = "queue_1";

  @Autowired
  private SenderService senderService;
  @Autowired
  private UserService userService;

  @RabbitHandler
  public void recieve(byte[] in) throws UnsupportedEncodingException, IOException {
    ObjectMapper mapper = new ObjectMapper();

      // JSON string to Java object
      String jsonString = new String(in, "UTF-8");
      RabbitMqMessage rabbitMqMessageIn = mapper.readValue(jsonString, RabbitMqMessage.class);

      switch (rabbitMqMessageIn.getActionCode()) {
        case ACTION_CODE_0:
          System.out.println("LOGIN ATTEMPT DETECTED!");
          String email = rabbitMqMessageIn.getEmail();
          char[] password = rabbitMqMessageIn.getPassword();

          RabbitMqMessage rabbitMqMessageOut = new RabbitMqMessage();
          rabbitMqMessageOut.setEmail(email);

          if (userService.loginValidation(email, password)) {
            rabbitMqMessageOut.setActionCode(ACTION_CODE_1);
          } else {
            rabbitMqMessageOut.setActionCode(ACTION_CODE_0);
          }

          ObjectMapper mapper2 = new ObjectMapper();

          // Java objects to JSON string - compact-print
          String jsonPayload = mapper2.writeValueAsString(rabbitMqMessageOut);
          System.out.println(jsonPayload);
//          Queue newQueue = new Queue("queue_1");
          senderService.send("queue_1", jsonPayload.getBytes());
            System.out.println("Message sent1");
      }
  }
}
