package com.bs.consultationsserver;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConsultationsServerApplication {

    public static void main(String[] args) throws IOException, TimeoutException {
        SpringApplication.run(ConsultationsServerApplication.class, args);
    }
}
