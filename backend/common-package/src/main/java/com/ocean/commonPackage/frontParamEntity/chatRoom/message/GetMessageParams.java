package com.ocean.commonPackage.frontParamEntity.chatRoom.message;

import com.ocean.commonPackage.anotation.ParamRename;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetMessageParams {
    @ParamRename("code")
    private String sender;
    @ParamRename("code")
    private String receiver;
}
