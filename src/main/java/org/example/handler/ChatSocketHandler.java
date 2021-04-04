package org.example.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import lombok.extern.log4j.Log4j;

@Log4j
public class ChatSocketHandler extends TextWebSocketHandler{
	List<WebSocketSession> webSocketSessionList = new ArrayList<WebSocketSession>();
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		String clientName="";
		if(session.getPrincipal().getName() != null) {
			clientName = session.getPrincipal().getName();	
		}else {
			clientName="비회원";
		}
		
		webSocketSessionList.add(session);
		log.info("접속한 사용자 : " + clientName+ ", 세션 아이디: "+session.getId());
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String sender = session.getPrincipal().getName();
		log.info("[" + sender + "] " + message.getPayload());
		for(WebSocketSession sess : webSocketSessionList) {
			sess.sendMessage(new TextMessage(sender + "|" + message.getPayload()));
		}
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		webSocketSessionList.remove(session);
		log.info(session.getPrincipal().getName() + "님이 퇴장했습니다.");
	}

		
}
