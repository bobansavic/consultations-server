/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bs.consultationsserver.service;

import com.bs.consultationsserver.model.MessageDto;
import com.bs.consultationsserver.model.Message;
import com.bs.consultationsserver.model.User;
import com.bs.consultationsserver.repository.MessageRepository;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Boban
 */
@Service
public class MessageService {
    
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserService userService;
    
    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }

    public Message saveMessage(MessageDto messageDto) {
      Message newMessage = new Message();
      newMessage.setReciever(userService.findByEmail(messageDto.getReceiver().getEmail()));
      newMessage.setSender(userService.findByEmail(messageDto.getSender().getEmail()));
      newMessage.setText(messageDto.getTextMessage());
      newMessage.setTimestamp(messageDto.getTimestamp());
      return saveMessage(newMessage);
    }
    
    public List<Message> saveMessages(Set<Message> messages) {
        Iterable<Message> savedMessages = messageRepository.saveAll(messages);
        return Lists.newArrayList(savedMessages);
    }
    
    public Message findByMessageId(Long id) {
        return messageRepository.findByMessageId(id);
    }
    
    public List<Message> findAllBySender(User sender) {
        return messageRepository.findAllBySender(sender);
    }

    public List<Message> findAllForUsers(Long userId1, Long userId2) {
        return messageRepository.findAllBySenderIdOrRecieverIdOrderByTimestamp(userId1, userId2);
    }

    public List<Message> findAllForUser(Long userId) {
        return messageRepository.findAllBySenderIdOrRecieverId(userId);
    }

    public Map<Long, List<MessageDto>> getClientMessages(Long userId) {
        Map<Long, List<MessageDto>> clientMessages = new HashMap<>();
        List<Long> allUserIds = userService.getAllUserIds();

        for (Long id : allUserIds) {
            List<Message> allMessages = findAllForUsers(userId, id);
            List<MessageDto> messageDtos = new ArrayList<>();

            for (Message msg : allMessages) {
                MessageDto messageDto = new MessageDto();
                messageDto.setReceiver(userService.createDtoFromUser(msg.getReciever()));
                messageDto.setSender(userService.createDtoFromUser(msg.getSender()));
                messageDto.setTextMessage(msg.getText());
                messageDto.setTimestamp(msg.getTimestamp());
                messageDtos.add(messageDto);
            }
            Comparator<MessageDto> timestampSorter = (a, b) -> a.getTimestamp().compareTo(b.getTimestamp());
            Collections.sort(messageDtos, timestampSorter.reversed());
            clientMessages.put(id, messageDtos);
        }
        return clientMessages;
    }
}
