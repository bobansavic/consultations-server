/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bs.consultationsserver.UserAndMessageTest;

import com.bs.consultationsserver.model.Message;
import com.bs.consultationsserver.model.User;
import com.bs.consultationsserver.service.MessageService;
import com.bs.consultationsserver.service.UserService;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Boban
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserAndMessageTest {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private MessageService messageService;
    
    private User user1;
    private User user2;
    private Message user1Message1;
    private Message user1Message2;
    private Message user1Message3;
    private Message user2Message1;
    private Message user2Message2;
    private Message user2Message3;
    
    @Before
    public void setUp() {
        user1 = new User("FirstNameTester1", "LastNameTester1", "tester1@test.com", "123".toCharArray());
        user2 = new User("FirstNameTester2", "LastNameTester2", "tester2@test.com", "321".toCharArray());
        
        user1Message1 = new Message("First message from user1.", new Date(), user1, user2);
        user1Message2 = new Message("Second message from user1.", new Date(), user1, user2);
        user1Message3 = new Message("Third message from user1.", new Date(), user1, user2);
        
        user2Message1 = new Message("First message from user2.", new Date(), user2, user1);
        user2Message2 = new Message("Second message from user2.", new Date(), user2, user1);
        user2Message3 = new Message("Third message from user2.", new Date(), user2, user1);
    }
    
    @Test
    public void testSaveUser() {
        User savedUser = userService.saveUser(user1);
        Assert.assertNotNull(savedUser);
    }
    
    @Test
    public void testFindUser() {
        User savedUser = userService.saveUser(user1);
        User foundUser = userService.findByFirstName(savedUser.getFirstName());
        Assert.assertNotNull(foundUser);
    }
    
    @Test
    public void testSaveMessages() {
        userService.saveUser(user1);
        userService.saveUser(user2);
        Set<Message> messages = new HashSet<>();
        messages.add(user1Message1);
        messages.add(user1Message2);
        messages.add(user1Message3);
        messages.add(user2Message1);
        messages.add(user2Message2);
        messages.add(user2Message3);
        List<Message> savedMessages = messageService.saveMessages(messages);
        
        Assert.assertNotNull(savedMessages);
        Assert.assertEquals(6, savedMessages.size());
    }
    
    @Test
    public void testFindMessagesByUser() {
        userService.saveUser(user1);
        userService.saveUser(user2);
        Set<Message> messages = new HashSet<>();
        messages.add(user1Message1);
        messages.add(user1Message2);
        messages.add(user1Message3);
        messageService.saveMessages(messages);
        List<Message> foundMessages = userService.getSentMessagesForUser(user1);
        Assert.assertNotNull(foundMessages);
        Assert.assertEquals(3, foundMessages.size());
    }
    
    @Test
    public void testFindMessagesForSenderAndReciever() {
        User user3 = new User("FirstNameTester3", "LastNameTester3", "tester3@test.com", "333".toCharArray());
        userService.saveUser(user1);
        userService.saveUser(user2);
        userService.saveUser(user3);
        Set<Message> messages = new HashSet<>();
        messages.add(user1Message1);
        messages.add(user1Message2);
        messages.add(user1Message3);
        messages.add(user2Message1);
        messages.add(user2Message2);
        messages.add(user2Message3);
        Message user3Message1 = new Message("First message from user3.", new Date(), user3, user1);
        messages.add(user3Message1);
        messageService.saveMessages(messages);
        List<Message> foundMessages = messageService.findAllForUsers(user1.getUserId(), user2.getUserId());
        
        Assert.assertNotNull(foundMessages);
        Assert.assertEquals(6, foundMessages.size());
    }
}
