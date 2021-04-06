package org.example.controller;

import org.example.domain.ChatRoomVO;
import org.example.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/chat/*")
@Log4j
public class ChatController {
	@Autowired
	private ChatService chatService;

	@GetMapping("/makeChat")
	public void makeChat() {

	}

	@PostMapping(value = "/makeChat")
	public String domakeChat(ChatRoomVO chatRoomVO) {
		log.info("채팅방 생성 객체 : " + chatRoomVO);
		int result = chatService.makeChatRoom(chatRoomVO);
		if (result == 1)
			log.info("채팅방 생성 완료!");
		return "redirect:/chat/myChatRooms";
	}

	@GetMapping("/myChatRooms")
	public void mkchat() {

	}

	@GetMapping("/allChatRooms")
	public void allChatRooms() {

	}
}
