package com.ocean;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class MQProducer8050Application {
    public static void main(String[] args) {
        SpringApplication.run(MQProducer8050Application.class, args);
    }
}
