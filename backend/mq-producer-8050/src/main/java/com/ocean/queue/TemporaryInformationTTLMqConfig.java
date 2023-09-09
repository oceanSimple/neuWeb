package com.ocean.queue;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

// 延迟队列配置
// 本队列不设置监听器，只是用来存储消息，消息过期后，会自动转发到死信队列
@Configuration
public class TemporaryInformationTTLMqConfig {
    public static final String TTL_EXCHANGE_NAME = "ttl_exchange";
    public static final String TTL_QUEUE_NAME = "ttl_queue";

    // 1. 交换机
    @Bean
    public Exchange ttlExchange() {
        // 声明交换机名字，是否持久化
        return ExchangeBuilder.directExchange(TTL_EXCHANGE_NAME).durable(true).build();
    }

    // 2. Queue队列
    @Bean
    public Queue ttlQueue() {
        HashMap<String, Object> args = new HashMap<>();
        // 设置队列中的消息10min过期
        args.put("x-message-ttl", 600000);
        // 设置绑定交换机
        args.put("x-dead-letter-exchange", TemporaryInformationDeadMqConfig.DEAD_EXCHANGE_NAME);
        // 设置绑定交换机的routingKey
        args.put("x-dead-letter-routing-key", "temporaryInformationDead");
        return QueueBuilder.durable(TTL_QUEUE_NAME).withArguments(args).build();
    }

    // 3. Binding---交换机和队列的绑定关系
    @Bean
    public Binding ttlBinding() {
        return BindingBuilder.bind(ttlQueue()).to(ttlExchange()).with("temporaryInformationTTL").noargs();
    }
}
