package com.ocean.commonPackage.frontParamEntity.chatRoom.friend;

import com.ocean.commonPackage.anotation.ParamRename;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddFriendByCodeParams {
    @ParamRename("code")
    private String userCode;
    @ParamRename("code")
    private String friendCode;
}
