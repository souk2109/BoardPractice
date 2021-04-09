package org.example.controller;

import java.util.List;

import org.example.domain.ChatRoomVO;
import org.example.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	
	// db로부터 chnum의 id를  받아옴 
	// chat?1321 과 같은 방식
	@GetMapping("/chat")
	public void ChatRoom(@RequestParam("chnum") int chnum, Model model) {
		// 1. 서비스로 getUserId(chnum)으로 모든 사용자 id를 가져와서 model에 담아 보냄, db에는 id|id|이런 식으로 저장!
		// 2. 뷰에서 받아서 split후 현재 로그인한 사용자 id와 비교 
		// 3. 일치하는 사용자가 있으면 채팅방을 보여줌
		// 4. 일치하지 않으면 경고문 후 redirect시킴
		String userId = chatService.getUserId(chnum);
		model.addAttribute("userId", userId);
	}
	
}
