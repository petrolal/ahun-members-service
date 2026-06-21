package com.petrolal.ahun.ahunmembersservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AhunMembersServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AhunMembersServiceApplication.class, args);
    }

}
