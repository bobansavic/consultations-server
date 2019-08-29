/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bs.consultationsserver.model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Boban
 */
public class MessageDto implements Serializable{
    
    private UserDto sender;
    private UserDto reciever;
    private String textMessage;
    private Date timestamp;

    public UserDto getSender() {
        return sender;
    }

    public void setSender(UserDto sender) {
        this.sender = sender;
    }

    public UserDto getReciever() {
        return reciever;
    }

    public void setReciever(UserDto reciever) {
        this.reciever = reciever;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
