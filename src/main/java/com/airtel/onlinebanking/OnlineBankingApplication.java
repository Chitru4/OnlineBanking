package com.airtel.onlinebanking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class OnlineBankingApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineBankingApplication.class, args);
    }

}
