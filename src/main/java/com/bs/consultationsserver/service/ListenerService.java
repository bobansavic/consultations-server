package com.bs.consultationsserver.service;

import com.bs.consultationsserver.model.MessageDto;
import com.bs.consultationsserver.model.RabbitMqMessage;
import com.bs.consultationsserver.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

@RabbitListener(queues = "server")
public class ListenerService {
    
    private static final String ACTION_CODE_0 = "ACTION_CODE_0";
    private static final String ACTION_CODE_0_0 = "ACTION_CODE_0_0";
    private static final String ACTION_CODE_0_1 = "ACTION_CODE_0_1";
    private static final String ACTION_CODE_1_0 = "ACTION_CODE_1_0";
    private static final String ACTION_CODE_1 = "ACTION_CODE_1";
    private static final String ACTION_CODE_1_1 = "ACTION_CODE_1_1";
    private static final String ACTION_CODE_2 = "ACTION_CODE_2";
    private static final String ACTION_CODE_3 = "ACTION_CODE_3";
    private static final String ACTION_CODE_5 = "ACTION_CODE_5";
    private static final String ACTION_CODE_5_0 = "ACTION_CODE_5_0";
    private static final String ACTION_CODE_5_1 = "ACTION_CODE_5_1";
    private static final String ACTION_CODE_6 = "ACTION_CODE_6";
    private static final String ACTION_CODE_6_0 = "ACTION_CODE_6_0";
    private static final String ACTION_CODE_6_1 = "ACTION_CODE_6_1";
    private final String QUEUE_1 = "queue_1";

    @Autowired
    private MessageService messageService;
    @Autowired
    private SenderService senderService;
    @Autowired
    private UserService userService;

    private String firstName;
    private String lastName;
    private String email;
    private char[] password;
    private RabbitMqMessage rabbitMessageIn;
    private RabbitMqMessage rabbitMessageOut;
    
    @RabbitHandler
    public void recieve(byte[] in) {
        
        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsonString = new String(in, "UTF-8");
            System.out.println("INCOMMING MESSAGE:\n" + jsonString);
            rabbitMessageIn = mapper.readValue(jsonString, RabbitMqMessage.class);
            System.out.println("RETURN QUEUE ID: " + rabbitMessageIn.getReturnQueueId());
        } catch (IOException ex) {
            Logger.getLogger(ListenerService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        switch (rabbitMessageIn.getActionCode()) {
            case ACTION_CODE_0:
                System.out.println("REGISTRATION ATTEMPT DETECTED!");
                firstName = rabbitMessageIn.getFirstName();
                lastName = rabbitMessageIn.getLastName();
                email = rabbitMessageIn.getEmail();
                password = rabbitMessageIn.getPassword();
                User newUser = new User(firstName, lastName, email, password);
                rabbitMessageOut = new RabbitMqMessage();
                try {
                    userService.saveUser(newUser);
                    rabbitMessageOut.setActionCode(ACTION_CODE_0_1);
                    rabbitMessageOut.setEmail(email);
                } catch (Exception ex) {
                    Logger.getLogger(ListenerService.class.getName())
                            .log(Level.SEVERE, "Error registering new user!", ex);
                    rabbitMessageOut.setActionCode(ACTION_CODE_0_0);
                    rabbitMessageOut.setErrorMessage(ex.getMessage());
                }
                String returnQueueId = rabbitMessageIn.getReturnQueueId();
                System.out.println("SENDING TO QUEUE: " + returnQueueId);
                senderService.send(returnQueueId, rabbitMessageOut);
                break;
            
            case ACTION_CODE_1:
                System.out.println("LOGIN ATTEMPT DETECTED!");
                email = rabbitMessageIn.getEmail();
                password = rabbitMessageIn.getPassword();
                
                rabbitMessageOut = new RabbitMqMessage();
                rabbitMessageOut.setEmail(email);
                
                User loginUser = userService.loginValidation(email, password);
                
                if (loginUser != null) {
                    rabbitMessageOut.setActionCode(ACTION_CODE_1_1);
                    rabbitMessageOut.setUserId(loginUser.getUserId());
                    rabbitMessageOut.setFirstName(loginUser.getFirstName());
                    rabbitMessageOut.setLastName(loginUser.getLastName());
                    rabbitMessageOut.setUniqueIdentifier(loginUser.getUniqueIdentifier());
                    rabbitMessageOut.setClientMessages(messageService.getClientMessages(loginUser.getUserId()));
                } else {
                    rabbitMessageOut.setActionCode(ACTION_CODE_1_0);
                }

                // Java objects to JSON string - compact-print
                String jsonPayload = "";
                try {
                    jsonPayload = mapper.writeValueAsString(rabbitMessageOut);
                } catch (JsonProcessingException ex) {
                    Logger.getLogger(ListenerService.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println(jsonPayload);
                senderService.send(rabbitMessageIn.getReturnQueueId(), jsonPayload.getBytes());
                System.out.println("Message sent1");
                break;
            
            case ACTION_CODE_3:
                System.out.println("REGISTRATION ATTEMPT DETECTED!");
                break;
            case ACTION_CODE_5:
                MessageDto chatMessage = rabbitMessageIn.getChatMessage();
                System.out.println(
                    "Chat message sent from client:\n" + rabbitMessageIn.getReturnQueueId()
                    + " (" + chatMessage.getSender().getEmail() + ")\nto client:\n"
                    + chatMessage.getReceiver().getUniqueIdentifier() + " ("
                    + chatMessage.getReceiver().getEmail() + ")"
                );
                senderService.forwardChatMessage(chatMessage);
                messageService.saveMessage(chatMessage);
                break;
            default:
                System.out.println("RabbitMqMessage recieved!");
                break;
        }
    }
}
