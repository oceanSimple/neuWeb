package com.ocean;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients     // 开启Feign
public class ChatRoomFriendApplication {
    public static void main(String[] args) {
        SpringApplication.run(ChatRoomFriendApplication.class, args);
    }
}
