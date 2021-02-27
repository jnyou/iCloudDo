package org.jnyou.messagepush;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MessagePushApplication {

    public static void main(String[] args) {
        SpringApplication.run(MessagePushApplication.class, args);
    }

}
