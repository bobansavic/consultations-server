/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bs.consultationsserver.configuration;

import com.bs.consultationsserver.service.ListenerService;
import com.bs.consultationsserver.service.RecieverService;
import com.bs.consultationsserver.service.SenderService;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Boban
 */
@Configuration
public class ConsulationsServerConfiguration {
    
    @Bean
    public SenderService senderService() {
        return new SenderService();
    }
    
    @Bean
    public ListenerService listenerService() {
        return new ListenerService();
    }
}
