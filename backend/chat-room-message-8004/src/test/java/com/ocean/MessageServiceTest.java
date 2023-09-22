package com.ocean;

import com.ocean.commonPackage.entity.chatRoom.Message;
import com.ocean.commonPackage.frontParamEntity.chatRoom.message.GetMessageParams;
import com.ocean.commonPackage.frontParamEntity.chatRoom.message.SetMessageIsReadParam;
import com.ocean.service.serviceImpl.MessageServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
public class MessageServiceTest {
    @Autowired
    private MessageServiceImpl service;

    //@Test
    public void getMessage() {
        printMessageList();
    }

    @Test
    @Order(1)
    public void addMessage() {
        System.out.println("添加消息前：");
        printMessageList();

        Message message = new Message("20216900", "00000000", "添加测试", "2023/12/30 15:00:00", 0);
        service.addMessage(message);

        System.out.println("添加消息后：");
        printMessageList();
    }

    @Test
    @Order(2)
    public void setMessageIsRead() {
        System.out.println("设置消息已读前：");
        printMessageList();

        SetMessageIsReadParam setMessageIsReadParam = new SetMessageIsReadParam("00000000", "20216900");
        service.setMessageIsRead(setMessageIsReadParam);

        System.out.println("设置消息已读后：");
        printMessageList();
    }

    public void printMessageList() {
        GetMessageParams getMessageParams = new GetMessageParams("00000000", "20216900");
        service.getMessage(getMessageParams).getData().forEach(System.out::println);
    }
}
