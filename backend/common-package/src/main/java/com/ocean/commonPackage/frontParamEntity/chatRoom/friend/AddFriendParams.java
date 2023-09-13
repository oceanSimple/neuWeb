package com.ocean.commonPackage.frontParamEntity.chatRoom.friend;

import com.ocean.commonPackage.entity.chatRoom.Friend;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddFriendParams {
    private String code;
    private Friend friend;
}
