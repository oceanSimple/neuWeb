package com.ocean.service;

import com.ocean.commonPackage.common.R;
import com.ocean.commonPackage.entity.chatRoom.Friend;
import com.ocean.commonPackage.frontParamEntity.chatRoom.friend.AddFriendByCodeParams;
import com.ocean.commonPackage.frontParamEntity.chatRoom.friend.AddFriendParams;
import com.ocean.commonPackage.frontParamEntity.chatRoom.friend.DeleteFriendParams;
import com.ocean.commonPackage.frontParamEntity.chatRoom.friend.UpdateFriendParams;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FriendService {
    // 通过用户code获取好友列表
    R<List<Friend>> getFriendList(String code);


    // 删除好友
    R<String> deleteFriend(DeleteFriendParams deleteFriendParams);

    // 更新好友
    R<String> updateFriend(UpdateFriendParams updateFriendParams);

    // 添加好友
    R<String> addFriend(AddFriendParams addFriendParams);

    // 通过传入好友的code添加好友
    R<String> addFriendByCode(AddFriendByCodeParams addFriendByCodeParams);

    // 将好友置顶，并设置有未读消息
    // 解释为什么用删除方法的参数：实质就是删除当前好友的位置，增加一步放在第一位的操作
    R<String> unshiftFriendToTop(DeleteFriendParams deleteFriendParams);
}
