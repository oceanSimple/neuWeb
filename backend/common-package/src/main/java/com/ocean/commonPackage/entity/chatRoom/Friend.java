package com.ocean.commonPackage.entity.chatRoom;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Friend {
    private String code; // 好友code
    private String nickName; // 昵称
    private String remark; // 备注
    private Integer hasNewMessage = 1; // 0: no new message; 1: has new message
}
