/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bs.consultationsserver.model;

import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;

/**
 *
 * @author Boban
 */
@EqualsAndHashCode
@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
//    @NotNull
//    @NotBlank
    private String firstName;
//    @NotNull
//    @NotBlank
    private String lastName;
//    @NotNull
//    @NotBlank
//    @Email
    @Column(unique = true)
    private String email;
//    @NotNull
    private char[] password;
    @OneToMany(mappedBy = "sender", fetch = FetchType.EAGER)
    private List<Message> sentMessages;
    @OneToMany(mappedBy = "reciever", fetch = FetchType.EAGER)
    private List<Message> recievedMessages;

    public User() {
    }

    public User(String firstName, String lastName, String email, char[] password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }
}
