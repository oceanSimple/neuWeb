package com.ocean.controller;

import com.ocean.commonPackage.anotation.CheckParam;
import com.ocean.commonPackage.anotation.ErrorLog;
import com.ocean.commonPackage.common.R;
import com.ocean.commonPackage.entity.chatRoom.Message;
import com.ocean.commonPackage.frontParamEntity.chatRoom.message.GetMessageParams;
import com.ocean.commonPackage.frontParamEntity.chatRoom.message.SetMessageIsReadParam;
import com.ocean.service.serviceImpl.MessageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/chatRoom/message")
public class MessageController {
    private final MessageServiceImpl service;

    @Autowired
    public MessageController(MessageServiceImpl service) {
        this.service = service;
    }

    @PostMapping("/add")
    @ErrorLog("添加消息")
    @CheckParam
    public R<Message> addMessage(@RequestBody Message message) {
        return service.addMessage(message);
    }

    @PostMapping("/getMessage")
    @ErrorLog("获取消息")
    @CheckParam
    public R<List<Message>> getMessage(@RequestBody GetMessageParams params) {
        return service.getMessage(params);
    }

    @PostMapping("/setMessageIsRead")
    @ErrorLog("设置消息已读")
    @CheckParam
    public R<String> setMessageIsRead(@RequestBody SetMessageIsReadParam param) {
        return service.setMessageIsRead(param);
    }
}
