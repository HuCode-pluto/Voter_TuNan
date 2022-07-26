package com.pluto.tnvote;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TnVoteApplication {

    public static void main(String[] args) {
        SpringApplication.run(TnVoteApplication.class, args);
    }

}
