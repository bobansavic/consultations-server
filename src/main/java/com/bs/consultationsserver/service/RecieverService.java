/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bs.consultationsserver.service;

import com.bs.consultationsserver.model.RabbitMqMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Boban
 */
//@Service
public class RecieverService {

    /*private static final String ACTION_CODE_0 = "ACTION_CODE_0";
    private static final String ACTION_CODE_1 = "ACTION_CODE_1";
    private final String QUEUE_1 = "queue_1";

    @Autowired
    private UserService userService;

    private Channel channel;
    private Connection connection;
    private Consumer consumer;
    private final String QUEUE_IDENT = "server";
    private final String HOST = "localhost";

    @PostConstruct
    public void init() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST);
        connection = factory.newConnection();
        channel = connection.createChannel();
        channel.queueDeclare(QUEUE_IDENT, false, false, false, null);

        System.out.println(" [*] Waiting for messages...");

        consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                    AMQP.BasicProperties properties, byte[] body)
                    throws IOException {

                *//*ByteArrayInputStream bis = new ByteArrayInputStream(body);
                ObjectInput in = null;

                try {
                    in = new ObjectInputStream(bis);
                    RabbitMqMessage payload = (RabbitMqMessage) in.readObject();

                    switch (payload.getActionCode()) {
                        case ACTION_CODE_0:
                            System.out.println("LOGIN ATTEMPT DETECTED!");
                            String email = payload.getEmail();
                            char[] password = payload.getPassword();
                            
                            RabbitMqMessage returnRabbitMqMessage = new RabbitMqMessage();
                            returnRabbitMqMessage.setEmail(email);

                            ByteArrayOutputStream bos = new ByteArrayOutputStream();
                            ObjectOutput out;
                            byte[] returnPayload = null;
                            try {
                                out = new ObjectOutputStream(bos);
                                out.writeObject(returnRabbitMqMessage);
                                out.flush();
                                returnPayload = bos.toByteArray();
                            } catch (IOException ex) {
                                Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
                            } finally {
                                try {
                                    bos.close();
                                } catch (IOException ex) {
                                    System.out.println("Oops! Exception: " + ex.getMessage());
                                }
                            }
                            if (userService.loginValidation(email, password)) {
                                returnRabbitMqMessage.setActionCode(ACTION_CODE_1);
                            } else {
                                returnRabbitMqMessage.setActionCode(ACTION_CODE_0);
                            }
                            channel.basicPublish("", payload.getReturnQueueId(), null, returnPayload);
                    }
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(RecieverService.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        if (in != null) {
                            in.close();
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(RecieverService.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }*//*
                ObjectMapper mapper = new ObjectMapper();

                try {
                    // JSON string to Java object
                    String jsonString = new String(body, "UTF-8");
                    RabbitMqMessage payload = mapper.readValue(jsonString, RabbitMqMessage.class);

                    switch (payload.getActionCode()) {
                        case ACTION_CODE_0:
                            System.out.println("LOGIN ATTEMPT DETECTED!");
                            String email = payload.getEmail();
                            char[] password = payload.getPassword();

                            RabbitMqMessage returnRabbitMqMessage = new RabbitMqMessage();
                            returnRabbitMqMessage.setEmail(email);

                            if (userService.loginValidation(email, password)) {
                                returnRabbitMqMessage.setActionCode(ACTION_CODE_1);
                            } else {
                                returnRabbitMqMessage.setActionCode(ACTION_CODE_0);
                            }

                            ObjectMapper mapper2 = new ObjectMapper();

                            // Java objects to JSON string - compact-print
                            String jsonString2 = mapper2.writeValueAsString(returnRabbitMqMessage);
                            System.out.println(jsonString2);
                            channel.queueDeclare(returnRabbitMqMessage.getReturnQueueId(), false, false, false, null);
                            channel.basicPublish("", returnRabbitMqMessage.getReturnQueueId(), null, jsonString2.getBytes());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        channel.basicConsume(QUEUE_IDENT, true, consumer);
    }*/
}
