package com.ocean.commonPackage.frontParamEntity.chatRoom.message;

import com.ocean.commonPackage.entity.chatRoom.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SetMessageIsReadParam {
    private String sender;
    private String receiver;
    private Message[] messages;
}
