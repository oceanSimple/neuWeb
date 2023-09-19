package com.ocean.service.serviceImpl;

import com.alibaba.fastjson2.JSON;
import com.ocean.commonPackage.common.R;
import com.ocean.commonPackage.common.RCode.RCodeEnum;
import com.ocean.commonPackage.entity.chatRoom.Message;
import com.ocean.commonPackage.frontParamEntity.chatRoom.message.GetMessageParams;
import com.ocean.commonPackage.frontParamEntity.chatRoom.message.SetMessageIsReadParam;
import com.ocean.service.MessageService;
import com.ocean.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    private final StringRedisTemplate stringRedisTemplate;

    /**
     * 构造器注入
     */
    @Autowired
    private MessageServiceImpl(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public R<Message> addMessage(Message message) {
        // 拼接key，00000000_20216928，小号在前，作为redis的key
        String key = Util.spliceSenderAndReceiver(message.getSender(), message.getReceiver());
        Long result = stringRedisTemplate.opsForList().rightPush(key, JSON.toJSONString(message));
        if (result != null && result > 0) {
            return R.success(RCodeEnum.SUCCESS.getCode(), "success", message);
        }
        return R.error(RCodeEnum.ERROR.getCode(), "添加消息失败");
    }

    @Override
    public R<List<Message>> getMessage(GetMessageParams params) {
        // 获取redis的key
        String key = Util.spliceSenderAndReceiver(params.getSender(), params.getReceiver());
        // 从redis中获取消息队列
        List<String> list = stringRedisTemplate.opsForList().range(key, 0, -1);
        if (list == null) {
            return R.error(RCodeEnum.ERROR.getCode(), "获取消息失败");
        }
        // 将redis中的string转成Message
        ArrayList<Message> result = new ArrayList<>(list.size());
        list.forEach(item -> result.add(JSON.parseObject(item, Message.class)));

        return R.success(RCodeEnum.SUCCESS.getCode(), "success", result);
    }

    @Override
    public R<String> setMessageIsRead(SetMessageIsReadParam param) {
        // 获取redis的key
        String key = Util.spliceSenderAndReceiver(param.getReceiver(), param.getSender());
        // 获取list
        List<String> list = stringRedisTemplate.opsForList().range(key, 0, -1);
        List<Message> messageList = new ArrayList<>(list.size());
        // 如果message的receiver是当前用户，且isRead为0，则将isRead设置为1
        for (int i = 0; i < list.size(); i++) {
            Message message = JSON.parseObject(list.get(i), Message.class);
            if (message.getReceiver().equals(param.getSender()) && message.getIsRead() == 0) {
                message.setIsRead(1);
            }
            messageList.add(message);
        }
        // 清空该用户与好友间的聊天记录
        stringRedisTemplate.delete(key);
        // 将新的list转成json字符串
        ArrayList<String> result = new ArrayList<>(list.size());
        messageList.forEach(item -> result.add(JSON.toJSONString(item)));
        stringRedisTemplate.opsForList().rightPushAll(key, result);

        return R.success(RCodeEnum.SUCCESS.getCode(), "success", "success");
    }
}
