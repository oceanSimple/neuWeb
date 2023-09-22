package com.ocean.commonPackage.frontParamEntity.chatRoom.friend;

import com.ocean.commonPackage.entity.chatRoom.Friend;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
// index与deleteFriendParams中的index相同
public class UpdateFriendParams {
    private String code; // 用户code
    private Integer index; // 好友列表中的索引
    private Friend friend; // 好友信息
}
