package com.ocean.server;

import com.alibaba.fastjson2.JSON;
import com.ocean.commonPackage.entity.chatRoom.Message;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint(value = "/ws/{code}")
public class WebSocketServer {
    private final static Logger log = LoggerFactory.getLogger(WebSocketServer.class);
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    //新：使用map对象优化，便于根据code来获取对应的WebSocket
    private static ConcurrentHashMap<String, WebSocketServer> websocketMap = new ConcurrentHashMap<>();
    //接收用户的code，指定需要推送的用户
    private String code;

    /**
     * 连接成功后调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("code") String code) {
        this.session = session;
        // 如果map中已经存在该code，则先删除，再加入
        if (websocketMap.get(code) != null) {
            websocketMap.remove(code);  //从map中删除
            subOnlineCount();           //在线数减1
        }
        websocketMap.put(code, this); //加入map中
        addOnlineCount();            //在线数加1
        log.info("有新用户登录:" + code + ",当前在线人数为" + getOnlineCount());
        this.code = code;
        //sendMessage("连接成功");
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if (websocketMap.get(this.code) != null) {
            websocketMap.remove(this.code);  //从map中删除
            subOnlineCount();           //在线数减1
            log.info("有一连接关闭！当前在线人数为" + getOnlineCount());
        }
    }

    /**
     * 收到客户端消息后调用的方法。
     * 收到消息后先村日redis中，再转发给目标客户端。
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("收到来自窗口" + code + "的信息:" + message);
        //saveToRedis(msg, code);
        if (StringUtils.isNotBlank(message)) {
            Message msg = JSON.parseObject(message, Message.class);
            WebSocketServer server = websocketMap.get(msg.getReceiver());
            if (server != null) {
                try {
                    server.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                // 如果用户不在线，则以系统账号发送消息给前端
                // 通知用户账户正在联系的好友不在线
                Message sysMsg = new Message("00000000", msg.getSender(), "好友不在线", null, 0);
                // 找到这个用户的websocket，发送消息
                WebSocketServer sysServer = websocketMap.get(msg.getSender());
                if (sysServer != null) {
                    try {
                        sysServer.sendMessage(JSON.toJSONString(sysMsg));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 发生错误时的回调函数
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }


    /**
     * 实现服务器主动推送消息
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    // 为了保证线程安全
    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }
}
