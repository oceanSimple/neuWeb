package com.ocean.service;

import com.ocean.commonPackage.common.R;
import com.ocean.commonPackage.entity.chatRoom.Message;
import com.ocean.commonPackage.frontParamEntity.chatRoom.message.GetMessageParams;
import com.ocean.commonPackage.frontParamEntity.chatRoom.message.SetMessageIsReadParam;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MessageService {
    R<Message> addMessage(Message message);
    R<List<Message>> getMessage(GetMessageParams params);
    R<String> setMessageIsRead(SetMessageIsReadParam param);
}
