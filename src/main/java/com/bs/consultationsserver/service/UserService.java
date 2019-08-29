/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bs.consultationsserver.service;

import com.bs.consultationsserver.model.Message;
import com.bs.consultationsserver.model.User;
import com.bs.consultationsserver.model.UserDto;
import com.bs.consultationsserver.repository.UserRepository;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Arrays;
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
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private MessageService messageService;

    List<Long> getAllUserIds() {
        List<Long> allUserIds = new ArrayList<>();
        List<User> allUsers = Lists.newArrayList(userRepository.findAll());
        for (User user : allUsers) {
            allUserIds.add(user.getUserId());
        }
        return allUserIds;
    }
    
    public User findByUserId(Long id) {
        return userRepository.findByUserId(id);
    }
    
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    public User findByFirstName(String firstName) {
        return userRepository.findByFirstName(firstName);
    }

    public List<UserDto> getAllUsersAsDto() {
        List<UserDto> users = new ArrayList<>();
        for (User user : userRepository.findAll()) {
            UserDto userDto = new UserDto(user.getUserId(), user.getFirstName(), user.getLastName(),
                user.getEmail(), user.getUniqueIdentifier());
            users.add(userDto);
        }
        return users;
    }

    public UserDto createDtoFromId(Long userId) {
        User user = findByUserId(userId);
        return new UserDto(user.getUserId(), user.getFirstName(), user.getLastName(),
            user.getEmail(), user.getUniqueIdentifier());
    }

    public UserDto createDtoFromUser(User user) {
        return new UserDto(user.getUserId(), user.getFirstName(), user.getLastName(),
            user.getEmail(), user.getUniqueIdentifier());
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
