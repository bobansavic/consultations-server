/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bs.consultationsserver.repository;

import com.bs.consultationsserver.model.Message;
import com.bs.consultationsserver.model.User;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Boban
 */
public interface MessageRepository extends CrudRepository<Message, Long>{
    Message findByMessageId(Long id);
    List<Message> findAllBySender(User sender);
    @Query("FROM Message WHERE (sender_id=:userId1 OR reciever_id=:userId1) AND (sender_id=:userId2 OR reciever_id=:userId2) ORDER BY timestamp ASC")
    List<Message> findAllBySenderIdOrRecieverIdOrderByTimestamp(Long userId1, Long userId2);
    List<Message> findAllBySenderIdOrRecieverId(Long userId);
}
