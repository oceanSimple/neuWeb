package com.ocean.service;

import com.ocean.commonPackage.common.R;
import com.ocean.commonPackage.entity.chatRoom.Friend;
import com.ocean.commonPackage.frontParamEntity.chatRoom.friend.AddFriendParams;
import com.ocean.commonPackage.frontParamEntity.chatRoom.friend.DeleteFriendParams;
import com.ocean.commonPackage.frontParamEntity.chatRoom.friend.UpdateFriendParams;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FriendService {
    R<List<Friend>> getFriendList(String code);
    R<String> deleteFriend(DeleteFriendParams deleteFriendParams);
    R<String> updateFriend(UpdateFriendParams updateFriendParams);
    R<String> addFriend(AddFriendParams addFriendParams);
}
