package com.ocean.controller;

import com.ocean.commonPackage.anotation.ErrorLog;
import com.ocean.commonPackage.common.R;
import com.ocean.commonPackage.common.RCode.RCodeEnum;
import com.ocean.util.SendEmail;
import com.ocean.util.Util;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mqProducer/temporaryInformation")
public class TemporaryInformationController {
    private final RabbitTemplate rabbitTemplate;
    private final StringRedisTemplate stringRedisTemplate;
    private final SendEmail sendEmail;

    @Autowired
    private TemporaryInformationController(RabbitTemplate rabbitTemplate, StringRedisTemplate stringRedisTemplate, SendEmail sendEmail) {
        this.rabbitTemplate = rabbitTemplate;
        this.stringRedisTemplate = stringRedisTemplate;
        this.sendEmail = sendEmail;
    }

    @GetMapping("/emailVerificationCode/{email}")
    @ErrorLog("发送邮箱验证码")
    public R<String> emailVerificationCode(@PathVariable("email") String email) {
        String code = Util.getVerificationCode();
        try {
            sendEmail.sendEmail(email, "neu-website验证码", "您的验证码为：" + code);
            // 将code存入redis
            stringRedisTemplate.opsForHash().put("emailVerificationCode", email, code);
            // 发送消息到ttl，设置过期时间为10分钟，十分钟后删除redis中的验证码
            rabbitTemplate.convertAndSend("ttl_exchange", "temporaryInformationTTL", email);
            return R.success(RCodeEnum.SUCCESS.getCode(), "发送邮件成功", "验证码已发送至您的邮箱");
        }catch (Exception e){
            return R.error(RCodeEnum.ERROR.getCode(), "发送邮件失败");
        }
    }
}
