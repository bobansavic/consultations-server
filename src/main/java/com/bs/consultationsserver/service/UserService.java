/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bs.consultationsserver.service;

import com.bs.consultationsserver.model.Message;
import com.bs.consultationsserver.model.User;
import com.bs.consultationsserver.repository.UserRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Boban
 */
@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private MessageService messageService;
    
    public User findByUserId(Long id) {
        return userRepository.findByUserId(id);
    }
    
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    public User findByFirstName(String firstName) {
        return userRepository.findByFirstName(firstName);
    }
    
    public List<Message> getSentMessagesForUser(User user) {
        return messageService.findAllBySender(user);
    }
    
    public User saveUser(User user) {
        return userRepository.save(user);
    }
    
    public User loginValidation(String email, char[] password) {
        try {
            User loginUser = findByEmail(email);
            if (loginUser != null) {
                System.out.println("User found! [" + email + "]");
                if (Arrays.equals(password, loginUser.getPassword())) {
                    System.out.println("Password match! Login success! [" + email + "]");
                    return loginUser;
                } else {
                    System.out.println("Incorrect password! [" + email + "]");
                    return null;
                }
            } else {
                System.out.println("User not found! [" + email + "]");
                return null;
            }
        } catch (Exception ex) {
            System.out.println("Authentication failed: " + ex);
            return null;
        }
    }
}
