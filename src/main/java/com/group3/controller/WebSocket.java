package com.group3.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import com.group3.service.ChatService;
import com.group3.service.impl.ChatServiceImpl;


@ServerEndpoint(value="/websocket/{selfid}/{aimid}")
@Component
public class WebSocket {
	
	@Autowired
	private ChatService chatService;
	private static WebSocket websocket;
	
	
	// 聊天室客户端ID
	String selfid = null;
	
	String aimid = null;

	// concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
	private static ConcurrentHashMap<String, WebSocket> webSocketMap = new ConcurrentHashMap<String, WebSocket>();

	// 与某个客户端的连接会话，需要通过它来给客户端发送数据
	private Session session;

	
	
    @PostConstruct
    public void init(){
    	websocket=this;
    	websocket.chatService=this.chatService;
    }
	
	/**
	 * 连接建立成功调用的方法
	 * 
	 * @param session
	 *            可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
	 */
	@OnOpen
	public void onOpen(@PathParam("selfid") String selfid,@PathParam("aimid") String aimid, Session session) {
		// 获取参数
		this.selfid = selfid;
		this.aimid = aimid;
		
		// System.out.println("新开一个连接：" + session.getRequestURI());

		this.session = session;
		webSocketMap.put(selfid+aimid, this);

		System.out.println("新开一个连接：" + session.getRequestURI() + "，登录总连接数为" + webSocketMap.size());
	}

	/**
	 * 连接关闭调用的方法
	 */
	@OnClose
	public void onClose() {
		webSocketMap.remove(this.selfid+this.aimid); // 从set中删除
		System.out.println("关闭一个连接：" + session.getRequestURI() + "，登录总连接数为" + webSocketMap.size());
	}

	/**
	 * 收到客户端消息后调用的方法
	 * 
	 * @param message
	 *            客户端发送过来的消息
	 * @param session
	 *            可选的参数
	 * @throws IOException 
	 */
	@OnMessage
	public void onMessage(String message, Session session) throws IOException {
		System.out.println("收到客户端:" + selfid + " 发的消息:" + message);
		// 获取toid，("TO#ID=" + toid + ":") +
		// 群聊("TO#GID=" + gid + ":")
		// document.getElementById('text').value;
		String toid = message.substring(2, message.indexOf(":"));
		System.out.println("wangbing:"+message);
		String content = message.substring(message.indexOf(":") + 1);
		String toSelfMsg = null;// 给自己回显一条消息
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		try {
			// 给自己回显一条消息
//			sendMessage("to " + toid + ":" + content);
			// 给对方发条消息

			if (webSocketMap.containsKey(toid+this.selfid)) {
				webSocketMap.get(toid+this.selfid).sendMessage("from " + selfid + ":" + content);
				websocket.chatService.produceRead(this.selfid, toid, timestamp, content, 1);
			} else {
				sendMessage("提醒： 不在线");
				
				System.out.println(this.selfid);
				System.out.println(toid);
				System.out.println(timestamp);
				System.out.println(content);
				websocket.chatService.produceRead(this.selfid, toid, timestamp, content, 0);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 发生错误时调用
	 * 
	 * @param session
	 * @param error
	 */
	@OnError
	public void onError(Session session, Throwable error) {
		System.out.println("发生错误");
		error.printStackTrace();
	}

	/**
	 * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
	 * 
	 * @param message
	 * @throws IOException
	 */
	public void sendMessage(String message) throws IOException {
		this.session.getBasicRemote().sendText(message);
		// this.session.getAsyncRemote().sendText(message);
	}
}
