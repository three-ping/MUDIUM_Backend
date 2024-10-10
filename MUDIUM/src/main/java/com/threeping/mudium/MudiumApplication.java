package com.threeping.mudium;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/* 설명. security ON/OFF */
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
//@SpringBootApplication
public class MudiumApplication {

    public static void main(String[] args) {
        SpringApplication.run(MudiumApplication.class, args);
    }

}
