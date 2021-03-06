package org.example.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.example.domain.ChatAction;
import org.example.domain.ChatMessageVO;
import org.example.domain.ChatParticipateVO;
import org.example.domain.ChatRoomVO;
import org.example.service.ChatMessageService;
import org.example.service.ChatParticipateSerivce;
import org.example.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.log4j.Log4j;

//메세지를 주고 받을 떄 {id:'', chatnum:''}와 같이 json형식으로 주고 받아야 보내는사람의 방 번호를 알 수 있다.
//json을 사용하기 위해 pom.xml에 json 라이브러리 추가해준다.
@Log4j
public class ChatSocketHandler extends TextWebSocketHandler {
	// 중복된 키(사용자 id)인 경우 덮어씌운다. 그리고 들어온 순서대로 값을 저장하기 위해서 LinkedHashMap을 사용했다.
	HashMap<String, WebSocketSession> socketSessions = new LinkedHashMap<String, WebSocketSession>();
	
	@Autowired
	private ChatMessageService chatMessageService;
	
	@Autowired
	private ChatService chatService;
	
	@Autowired
	private ChatParticipateSerivce participateSerivce;
	
	private ObjectMapper jsonMapper = new ObjectMapper();
	private ChatMessageVO msgObj = new ChatMessageVO();
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		String userId = getUserId(session);
		/*
		 * 이 부분에서 채팅방에 처음 들어온 사용자인 경우에만 id를 키로 하는 현재의 세션을 저장하려고 했는데 에러가 발생했다. 이유는 새로고침 할
		 * 때나 재접속시 webSocketSession이 바뀌기 때문이다. 그래서 처음 들어온 경우에는 해당id로 세션을 생성해주면 되고, 기존에
		 * 접속한 사용자인 경우 해당 id에 해당하는 세션값을 덮어씌워주면 된다.
		 */
		socketSessions.put(userId, session);
		log.info(userId + "의 소켓은 정상적!");
		log.info(socketSessions);
	}

	// 메세지를 받는 역할읗 한다.
	// 메세지를 받을 떄 메세지의 타입(퇴장,참여 등)을 받아서 그때 마다의 처리를 해주면 된다.
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String sender = getUserId(session); // 메세지를 보낸사람
		String rawMessage = message.getPayload(); // json형태의 메세지
		msgObj = jsonMapper.readValue(rawMessage, ChatMessageVO.class); // json형식의 문자를 특정 클래스로 캐스팅(? 담아준다)

		// action이 send, out, join인 경우 db에 저장 후 jsp페이지에 전송
		if (msgObj.getAction() == ChatAction.SEND || msgObj.getAction() == ChatAction.OUT || msgObj.getAction() == ChatAction.JOIN) {
			chatMessageService.insertChatMessage(msgObj);
			// 받은 메세지를 모든 사용자들에게 전송(여기서 특정한 채널에 전송을 해야함)
			socketSessions.forEach((userId, sess) -> {
				try {
					// jsp페이지에 보낼 메세지
					String passToJspMessage = msgObj.getAction() + "|" + msgObj.getChnum() + "|" + msgObj.getSender() + "|" + msgObj.getMessage() + "|" + msgObj.getId();
					sess.sendMessage(new TextMessage(passToJspMessage));
					log.info("action : "+msgObj.getAction());
				} catch (IOException e) {
					log.info("전송 에러!");
					e.printStackTrace();
				}
			});
		}

		// // action이 see, unsee인 경우 db에 저장 안하고 jsp페이지에 바로 전송
		else if (msgObj.getAction() == ChatAction.SEE || msgObj.getAction() == ChatAction.UNSEE) {
			socketSessions.forEach((userId, sess) -> {
				try {
					log.info("action : "+msgObj.getAction());
					String passToJspMessage = msgObj.getAction() + "|" + msgObj.getChnum() + "|" + msgObj.getSender() + "|" + msgObj.getId();
					sess.sendMessage(new TextMessage(passToJspMessage));
				} catch (IOException e) {
					log.info("전송 에러!");
					e.printStackTrace();
				}
			});
		}

		log.info("모두에게 전송하기 전 세션 리스트 : " + socketSessions);

	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		String userId = getUserId(session);
		String sender = getNickname(userId);
		socketSessions.remove(userId);
		
		log.info("새로고침 후 변경된 소켓 세션 목록 : " + socketSessions);
		System.out.println("채팅창 나간사람: " + userId + " 상태: " + status);
		
		ChatParticipateVO participateVO = participateSerivce.getEnableVO(userId);
		if(participateVO != null) {
			int chnum = participateVO.getChnum();
			String id = participateVO.getId();
			participateSerivce.updateOutChatParticipateVO(chnum, id);
			// TODO enable을 0으로 변경 되었으므로 해당 id와  chnum을 사용해서 부재중임을 msg로 알려야한다.
			socketSessions.forEach((sessId, sess) -> {
				try { // jsp페이지에 보낼 메세지 String String
					String passToJspMessage = ChatAction.UNSEE + "|" + chnum + "|" + sender + "|" + userId;
					sess.sendMessage(new TextMessage(passToJspMessage));
					log.info("---채팅방 나갔으므로 out표시 -----------------> " + passToJspMessage);
				} catch (IOException e) {
					log.info("전송 에러!");
					e.printStackTrace();
				}
			});
		}
	}

	// 웹소켓 세션으로부터 사용자의 id를 받아옴
	private String getUserId(WebSocketSession session) {
		return session.getPrincipal().getName();
	}
	
	// 웹소켓 세션으로부터 사용자의 닉네임을 받아옴
	private String getNickname(String userId) {
		return chatService.getUserById(userId).getNickname();
	}

}
