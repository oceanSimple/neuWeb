package com.ocean.commonPackage.frontParamEntity.chatRoom.message;

import com.ocean.commonPackage.anotation.ParamRename;
import com.ocean.commonPackage.entity.chatRoom.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SetMessageIsReadParam {
    @ParamRename("code")
    private String sender;
    @ParamRename("code")
    private String receiver;
    private Message[] messages;
}
