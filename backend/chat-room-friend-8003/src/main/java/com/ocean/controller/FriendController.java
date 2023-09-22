package com.ocean.controller;

import com.ocean.commonPackage.anotation.CheckParam;
import com.ocean.commonPackage.anotation.ErrorLog;
import com.ocean.commonPackage.common.R;
import com.ocean.commonPackage.entity.chatRoom.Friend;
import com.ocean.commonPackage.entity.user.User;
import com.ocean.commonPackage.frontParamEntity.chatRoom.friend.AddFriendByCodeParams;
import com.ocean.commonPackage.frontParamEntity.chatRoom.friend.AddFriendParams;
import com.ocean.commonPackage.frontParamEntity.chatRoom.friend.DeleteFriendParams;
import com.ocean.commonPackage.frontParamEntity.chatRoom.friend.UpdateFriendParams;
import com.ocean.service.UserService;
import com.ocean.service.serviceImpl.FriendServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chatRoom/friend")
public class FriendController {
    // friend服务
    private final FriendServiceImpl friendService;

    @Autowired
    public FriendController(FriendServiceImpl service) {
        this.friendService = service;
    }

    /**
     * 通过code获取好友列表
     * @param code  用户code
     * @return 好友列表的list
     */
    @GetMapping("/{code}")
    @ErrorLog("获取好友列表")
    @CheckParam
    public R<List<Friend>> getFriendList(@PathVariable("code") String code) {
        return friendService.getFriendList(code);
    }

    /**
     * 删除好友
     */
    @DeleteMapping
    @ErrorLog("删除好友")
    @CheckParam
    public R<String> deleteFriend(@RequestBody DeleteFriendParams deleteFriendParams) {
        return friendService.deleteFriend(deleteFriendParams);
    }

    /**
     * 更新好友信息
     */
    @PutMapping
    @ErrorLog("更新好友信息")
    @CheckParam
    public R<String> updateFriend(@RequestBody UpdateFriendParams params) {
        return friendService.updateFriend(params);
    }

    /**
     * 添加好友
     */
    @PostMapping
    @ErrorLog("添加好友")
    @CheckParam
    public R<String> addFriend(@RequestBody AddFriendParams addFriendParams) {
        return friendService.addFriend(addFriendParams);
    }

    /**
     * 将好友置顶，并设置有未读消息
     */
    @PutMapping("/unshiftFriendToTop")
    @ErrorLog("将好友置顶，并设置有未读消息")
    @CheckParam
    public R<String> unshiftFriendToTop(@RequestBody DeleteFriendParams deleteFriendParams) {
        return friendService.unshiftFriendToTop(deleteFriendParams);
    }

    /**
     * 通过code添加好友
     */
    @PostMapping("/addFriendByCode")
    @ErrorLog("通过code添加好友")
    @CheckParam
    public R<String> addFriendByCode(@RequestBody AddFriendByCodeParams param) {
        return friendService.addFriendByCode(param);
    }
}
