package com.ocean.commonPackage.frontParamEntity.chatRoom.friend;

import com.ocean.commonPackage.anotation.ParamRename;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * 1. 好友是在redis用index进行存储
 * 2. 所以好友的获取是有序的
 * 3. 通过前端传递index-好友在列表中的索引，进行删除
 */
public class DeleteFriendParams {
    @ParamRename("code")
    private String userCode; // 用户的code
    private Integer index; // 要删除的好友的index-redis的list中的索引
}
