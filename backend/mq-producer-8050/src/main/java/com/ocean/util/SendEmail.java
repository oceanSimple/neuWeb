package com.ocean.util;

import com.ocean.entity.EmailParam;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class SendEmail {
    private static JavaMailSenderImpl javaMailSender;
    private static SimpleMailMessage message;

    public SendEmail(EmailParam emailParam) {
        // 初始化JavaMailSenderImpl
        javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(emailParam.getHost());
        javaMailSender.setPort(emailParam.getPort());
        javaMailSender.setUsername(emailParam.getUsername());
        javaMailSender.setPassword(emailParam.getPassword());
        javaMailSender.setProtocol(emailParam.getProtocol());
        javaMailSender.setDefaultEncoding(emailParam.getDefaultEncoding());

        // 初始化SimpleMailMessage
        message = new SimpleMailMessage();
        message.setFrom(emailParam.getFrom());

        // 初始化配置
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.timeout", "25000");
        javaMailSender.setJavaMailProperties(properties);
    }

    public void sendEmail(String targetEmail, String title, String content) {
        message.setTo(targetEmail);
        message.setSubject(title);
        message.setText(content);
        // 发送邮件
        javaMailSender.send(message);
    }
}
