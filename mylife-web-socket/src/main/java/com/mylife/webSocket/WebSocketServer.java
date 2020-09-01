package com.mylife.webSocket;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.CopyOnWriteArraySet;
/**
 * @descirption : 主webSocket
 * @author : wyh
 * @date : 2020/9/1 11:07
 */
@ServerEndpoint("/webSocket/{sid}")
@Component
public class WebSocketServer {

    private static CopyOnWriteArraySet<WebSocketServer> webSocketServerSet = new CopyOnWriteArraySet<>();
    private Session session;
    private String sid;
    private static Log log = LogFactory.getLog(WebSocketServer.class);

    /**
     * 建立连接成功调用
     * @param session
     * @param sid
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("sid")String sid){
        this.session = session;
        this.sid = sid;
        webSocketServerSet.add(this);
    }

    /**
     * 关闭连接时调用
     */
    @OnClose
    public void onClose(){
        webSocketServerSet.remove(this);
    }

    /**
     * 收到客户端信息
     * @param session
     * @param message
     */
    @OnMessage
    public void onMessage(Session session,String message){
        System.out.println("收到客户端" + this.sid + "的消息："+message);
        this.sendMessage(session,message);
    }

    /**
     * 错误时调用
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session,Throwable error){
        log.error(sid + "连接发生错误：" + ExceptionUtils.getStackTrace(error));
    }

    /**
     * 发送消息
     * @param session
     * @param message
     */
    public void sendMessage(Session session,String message){
        if(session != null && StringUtils.isNotBlank(message)){
            //在高并发的情况下，会抛出上面的异常（多个线程同时使用同一session发送的原因）
            if (session.isOpen()) {
                synchronized (session) {
                    try {
                        session.getAsyncRemote().sendText(message);
                    } catch (Exception e) {
                        log.error("发送任务websocket消息异常:" + ExceptionUtils.getStackTrace(e));
                    }
                }
            }
        }
    }

    /**
     * 群发消息（向连接的所有客户端发送消息）
     * @param message
     */
    public void massSendMessage(String message){
        if(StringUtils.isNotBlank(message)){
            for (WebSocketServer wss:webSocketServerSet) {
                this.sendMessage(wss.session,message);
            }
        }
    }
}
