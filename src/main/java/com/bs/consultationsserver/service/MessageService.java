/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bs.consultationsserver.service;

import com.bs.consultationsserver.model.Message;
import com.bs.consultationsserver.model.User;
import com.bs.consultationsserver.repository.MessageRepository;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.List;
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
    
    public Message saveMessage(Message message) {
        return messageRepository.save(message);
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
}
