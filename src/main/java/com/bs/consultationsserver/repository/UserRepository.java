/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bs.consultationsserver.repository;

import com.bs.consultationsserver.model.User;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Boban
 */
public interface UserRepository extends CrudRepository<User, Long>{
    User findByUserId(Long id);
    User findByEmail(String email);
    User findByFirstName(String firstName);
}
