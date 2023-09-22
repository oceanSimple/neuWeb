package com.ocean;

import com.ocean.commonPackage.common.R;
import com.ocean.commonPackage.common.RCode.RCodeEnum;
import com.ocean.commonPackage.entity.chatRoom.Friend;
import com.ocean.commonPackage.frontParamEntity.chatRoom.friend.AddFriendByCodeParams;
import com.ocean.commonPackage.frontParamEntity.chatRoom.friend.AddFriendParams;
import com.ocean.commonPackage.frontParamEntity.chatRoom.friend.DeleteFriendParams;
import com.ocean.commonPackage.frontParamEntity.chatRoom.friend.UpdateFriendParams;
import com.ocean.service.serviceImpl.FriendServiceImpl;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FriendServiceTest {
    private final Logger log = LoggerFactory.getLogger(FriendServiceTest.class);

    @Autowired
    private FriendServiceImpl service;

    @Test
    @Order(0)
    public void getFriendList() {
        service.getFriendList("00000000");
        System.out.println("初始好友列表如下:");
        printFriendList();
    }

    @Test
    @Order(1)
    public void addFriend() {
        System.out.println("添加前好友列表如下:");
        printFriendList();

        Friend friend = new Friend("99999999", "nickName", "remark", 1);
        R<String> result = service.addFriend(new AddFriendParams("00000000", friend));

        System.out.println("添加后好友列表如下:");
        printFriendList();

        if (result.getCode().equals(RCodeEnum.SUCCESS.getCode())) {
            log.info("添加好友成功");
        } else {
            log.info("添加好友失败");
        }
    }

    @Test
    @Order(2)
    public void updateFriend() {
        System.out.println("修改前好友列表如下:");
        printFriendList();

        Friend friend = new Friend("99999999", "修改后", "修改后", 1);
        UpdateFriendParams updateFriendParams = new UpdateFriendParams("00000000", 0, friend);
        R<String> result = service.updateFriend(updateFriendParams);

        System.out.println("修改后好友列表如下:");
        printFriendList();

        if (result.getCode().equals(RCodeEnum.SUCCESS.getCode())) {
            log.info("修改好友成功");
        } else {
            log.info("修改好友失败");
        }
    }

    @Test
    @Order(3)
    public void unshiftFriendToTop() {
        System.out.println("置顶前好友列表如下:");
        printFriendList();

        DeleteFriendParams deleteFriendParams = new DeleteFriendParams("00000000", 1);
        R<String> result = service.unshiftFriendToTop(deleteFriendParams);

        System.out.println("置顶后当前好友列表如下:");
        printFriendList();

        if (result.getCode().equals(RCodeEnum.SUCCESS.getCode())) {
            log.info("置顶好友成功");
        } else {
            log.info("置顶好友失败");
        }
    }

    @Test
    @Order(4)
    public void deleteFriend() {
        System.out.println("删除前好友列表如下:");
        printFriendList();

        DeleteFriendParams deleteFriendParams = new DeleteFriendParams("00000000", 1);
        R<String> result = service.deleteFriend(deleteFriendParams);

        System.out.println("删除后好友列表如下:");
        printFriendList();

        if (result.getCode().equals(RCodeEnum.SUCCESS.getCode())) {
            log.info("删除好友成功");
        } else {
            log.info("删除好友失败");
        }
    }

    public void printFriendList() {
        service.getFriendList("00000000").getData().forEach(System.out::println);
    }
}
