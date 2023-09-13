package com.ocean.commonPackage.frontParamEntity.chatRoom.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetMessageParams {
    private String sender;
    private String receiver;
}
