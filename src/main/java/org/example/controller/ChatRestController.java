package org.example.controller;

import java.util.List;

import org.example.domain.ChatMyRoomRequestVO;
import org.example.domain.ChatRoomVO;
import org.example.domain.ChatUserCurrentState;
import org.example.domain.ChatUserValidateVO;
import org.example.domain.ReplyVO;
import org.example.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping("/chat/*")
@Log4j
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
	 
	@GetMapping(value = "/allChatRoom/{id}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<ChatUserCurrentState>> getAllChatRooms(@PathVariable("id") String id){
		List<ChatUserCurrentState> allRoomList = chatService.getAllList(id);
		return new ResponseEntity<List<ChatUserCurrentState>>(allRoomList, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/delete/{chnum}", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> deleteRoom(@PathVariable("chnum") int chnum){
		int _result = chatService.deleteRoom(chnum);
		String result = _result == 1 ? "success": "fail"; 
		return new ResponseEntity<String> (result,HttpStatus.OK);
	}
	
	@RequestMapping(method = { RequestMethod.PUT,RequestMethod.PATCH }, 
			value = "/update/{chnum}", consumes = "application/json" , produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> updateRoom(@RequestBody ChatRoomVO chatRoomVO, @PathVariable("chnum") int chnum){
		chatService.updateRoom(chatRoomVO);
		return new ResponseEntity<String>("", HttpStatus.OK);
	}
	
	@PostMapping(value = "/request/{chnum}", consumes = "application/json", produces = { MediaType.TEXT_PLAIN_VALUE })
	public ResponseEntity<String> joinRequest(@RequestBody ChatUserValidateVO userValidateVO) {
		int result = chatService.joinRequest(userValidateVO);
		return result == 1 ? new ResponseEntity<String>("success", HttpStatus.OK)
				: new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping(value = "/getRequests/{id}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<ChatMyRoomRequestVO>> getMyRoomRequest(@PathVariable("id") String id){
		List<ChatMyRoomRequestVO> roomRequests = chatService.getMyRoomRequests(id);
		return new ResponseEntity<List<ChatMyRoomRequestVO>>(roomRequests, HttpStatus.OK);
	}
	
	@RequestMapping(method = { RequestMethod.PUT,RequestMethod.PATCH }, 
			value = "/updateValidate", consumes = "application/json" , produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> updateValidate(@RequestBody ChatUserValidateVO chatUserValidateVO){
		chatService.updateRequest(chatUserValidateVO);
		return new ResponseEntity<String>("", HttpStatus.OK);
	}
}












