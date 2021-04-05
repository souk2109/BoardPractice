package org.example.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.example.domain.ChatAction;
import org.example.domain.ChatMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.log4j.Log4j;

//메세지를 주고 받을 떄 {id:'', chatnum:''}와 같이 json형식으로 주고 받아야 보내는사람의 방 번호를 알 수 있다.
//json을 사용하기 위해 pom.xml에 json 라이브러리 추가해준다.
@Log4j
public class ChatSocketHandler extends TextWebSocketHandler{
	// 중복된 키(사용자 id)인 경우 덮어씌운다. 그리고 들어온 순서대로 값을 저장하기 위해서 LinkedHashMap을 사용했다.

	HashMap<String, WebSocketSession> socketSessions = new LinkedHashMap<String, WebSocketSession>();
	 
	ObjectMapper jsonMapper = new ObjectMapper();
	ChatMessage msgObj = new ChatMessage();
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		String userId = getUserId(session);

		/*
		 * 이 부분에서 채팅방에 처음 들어온 사용자인 경우에만 id를 키로 하는 현재의 세션을 저장하려고 했는데 에러가 발생했다. 이유는 새로고침 할
		 * 때나 재접속시 webSocketSession이 바뀌기 때문이다. 그래서 처음 들어온 경우에는 해당id로 세션을 생성해주면 되고, 기존에
		 * 접속한 사용자인 경우 해당 id에 해당하는 세션값을 덮어씌워주면 된다.
		 */

		// 세션이 있는 경우 (이미 참여중인 사용자)
		if (socketSessions.get(userId) != null) {
			log.info("채팅방 방금 들어온 id: " + userId);
		} else {
			log.info("처음 들어온 id: " + userId);
		}
		socketSessions.put(userId, session);
	}
	
	// 메세지를 받는 역할읗 한다. 
	// 메세지를 받을 떄 메세지의 타입(퇴장,참여 등)을 받아서 그때 마다의 처리를 해주면 된다.
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String sender = getUserId(session); // 메세지를 보낸사람
		String rawMessage = message.getPayload(); // json형태의 메세지
		msgObj = jsonMapper.readValue(rawMessage, ChatMessage.class); // json형식의 문자를 특정 클래스로 캐스팅(? 담아준다)
		
		// 일반 전송한 경우
		if (msgObj.getAction() == ChatAction.SEND) {
			log.info("[일반 전송] " + msgObj.getChnum() + "방에 " + msgObj.getSender() + "가 [" + msgObj.getMessage() + "]를 전송함");
		}
		
		// 채팅방 퇴장한 경우
		else if (msgObj.getAction() == ChatAction.OUT) {
			socketSessions.remove(sender);
			log.info(sender + "와의 연결이 끊김!");
			log.info("[퇴장전송] " + msgObj.getChnum() + "방에" + msgObj.getSender() + "가 나갔음" + msgObj.getMessage() + "]");
		}

		// 받은 메세지를 모든 사용자들에게 전송
				socketSessions.forEach((userId, sess)->{
					try {
						// jsp페이지에 보낼 메세지
						String passToJspMessage = sender +"|"+msgObj.getMessage();
						sess.sendMessage(new TextMessage(passToJspMessage));
						log.info("--------------------> " +getUserId(sess) + "에게  [" + passToJspMessage + "] 전달함");
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		System.out.println("채팅창 닫은사람: "+ msgObj.getSender()+" 상태: " + status);
	}
	
	// 웹소켓 세션으로부터 사용자의 id를 받아옴
	private String getUserId(WebSocketSession session) {
		return session.getPrincipal().getName();
	}
}
