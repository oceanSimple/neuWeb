package com.ocean.commonPackage.entity.chatRoom;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private String sender; // 发送者
    private String receiver; // 接收者
    private String message; // 消息
    private String time; // 时间
    private Integer isRead; // 0: not read; 1: read
}
