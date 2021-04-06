package org.example.controller;

import java.util.List;

import org.example.domain.ChatRoomVO;
import org.example.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat/*")
public class ChatRestController {
	@Autowired
	private ChatService chatService;
	
	// json형식으로 리스트 데이터를 전송
	@GetMapping(value = "/myChatRoom/{id}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<ChatRoomVO>> getMyChatRoom(@PathVariable("id") String id){
		List<ChatRoomVO> myRoomList = chatService.getMyList(id);
		return new ResponseEntity<List<ChatRoomVO>>(myRoomList, HttpStatus.OK);
	}
	 
	@GetMapping(value = "/allChatRoom", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<ChatRoomVO>> getAllChatRooms(){
		List<ChatRoomVO> allRoomList = chatService.getAllList();
		return new ResponseEntity<List<ChatRoomVO>>(allRoomList, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/delete/{chnum}", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> deleteRoom(@PathVariable("chnum") int chnum){
		int _result = chatService.deleteRoom(chnum);
		String result = _result == 1 ? "success": "fail"; 
		return new ResponseEntity<String> (result,HttpStatus.OK);
	}
}
