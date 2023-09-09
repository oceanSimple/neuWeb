package com.ocean.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 读取配置文件中的email参数
 */
@Component
@Data
@ConfigurationProperties(prefix = "email")
public class EmailParam {
    // JavaMailSenderImpl的参数
    private String host; //设置发送方的邮箱格式
    private String protocol; //设置协议
    private String username; //设置发送方的邮箱
    private String password; //设置发送方的邮箱的授权码
    private int port; //设置端口号
    private String defaultEncoding; //设置默认编码格式

    // SimpleMailMessage的参数
    private String from; //设置发送方的邮箱，必须与username相同
}
