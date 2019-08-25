/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bs.consultationsserver.model;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;

/**
 *
 * @author Boban
 */
@EqualsAndHashCode
@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;
    @NotNull
    private String text;
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;
    @ManyToOne(fetch = FetchType.EAGER, optional = false) // could be LAZY
    @JoinColumn(name = "senderId", nullable = false)
    private User sender;
    @ManyToOne(fetch = FetchType.EAGER, optional = false) // could be LAZY
    @JoinColumn(name = "recieverId", nullable = false)
    private User reciever;

    public Message() {
    }

    public Message(String text, Date timestamp, User sender, User reciever) {
        this.text = text;
        this.timestamp = timestamp;
        this.sender = sender;
        this.reciever = reciever;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReciever() {
        return reciever;
    }

    public void setReciever(User reciever) {
        this.reciever = reciever;
    }
}
