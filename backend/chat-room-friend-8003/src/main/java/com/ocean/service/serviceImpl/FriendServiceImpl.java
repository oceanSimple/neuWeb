package com.ocean.service.serviceImpl;

import com.alibaba.fastjson2.JSON;
import com.ocean.commonPackage.common.R;
import com.ocean.commonPackage.common.RCode.RCodeEnum;
import com.ocean.commonPackage.entity.chatRoom.Friend;
import com.ocean.commonPackage.frontParamEntity.chatRoom.friend.AddFriendParams;
import com.ocean.commonPackage.frontParamEntity.chatRoom.friend.DeleteFriendParams;
import com.ocean.commonPackage.frontParamEntity.chatRoom.friend.UpdateFriendParams;
import com.ocean.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FriendServiceImpl implements FriendService {
    private final StringRedisTemplate stringRedisTemplate;

    @Autowired
    public FriendServiceImpl(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public R<List<Friend>> getFriendList(String code) {
        // 获取所有的好友列表
        List<String> list = stringRedisTemplate.opsForList().range(code, 0, -1);
        if (list.size() == 0) {
            return R.error(RCodeEnum.NO_DATA.getCode(), "暂无好友");
        }

        // 将string转换成friend对象
        ArrayList<Friend> friends = new ArrayList<>(list.size());
        list.stream().forEach(item -> {
                    Friend friend = JSON.parseObject(item, Friend.class);
                    friends.add(friend);
                }
        );
        return R.success(RCodeEnum.SUCCESS.getCode(), "获取好友列表成功", friends);
    }

    @Override
    public R<String> deleteFriend(DeleteFriendParams deleteFriendParams) {
        // 通过index将redis中的指定项修改成标记string
        stringRedisTemplate.opsForList().set(deleteFriendParams.getUserCode(), deleteFriendParams.getIndex(), "delete");
        // 删除指定项
        Long delete = stringRedisTemplate.opsForList().remove(deleteFriendParams.getUserCode(), 1, "delete");
        if (delete == 0) {
            return R.error(RCodeEnum.ERROR.getCode(), "fail");
        }
        return R.success(RCodeEnum.SUCCESS.getCode(), "success", "success");
    }

    @Override
    public R<String> updateFriend(UpdateFriendParams updateFriendParams) {
        try {
            stringRedisTemplate.opsForList().set(updateFriendParams.getCode(), updateFriendParams.getIndex(), JSON.toJSONString(updateFriendParams.getFriend()));
            return R.success(RCodeEnum.SUCCESS.getCode(), "success", "success");
        } catch (Exception e) {
            return R.error(RCodeEnum.ERROR.getCode(), "fail");
        }
    }

    @Override
    public R<String> addFriend(AddFriendParams addFriendParams) {
        // 将friend转换成字符串，左插入list中
        String friendJSON = JSON.toJSONString(addFriendParams.getFriend());
        Long flag = stringRedisTemplate.opsForList().leftPush(addFriendParams.getCode(), friendJSON);
        if (flag == 0)
            return R.error(RCodeEnum.ERROR.getCode(), "添加失败");
        return R.success(RCodeEnum.SUCCESS.getCode(), "添加成功", "success");
    }
}
