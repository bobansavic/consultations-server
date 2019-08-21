/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bs.consultationsserver.model;

import java.util.Date;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;

/**
 *
 * @author Boban
 */
@EqualsAndHashCode
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
}
