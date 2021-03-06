package org.example.controller;

import java.util.List;

import org.example.domain.ChatMessageVO;
import org.example.domain.ChatMyRoomRequestVO;
import org.example.domain.ChatParticipateVO;
import org.example.domain.ChatRoomVO;
import org.example.domain.ChatUserCurrentState;
import org.example.domain.ChatUserValidateVO;
import org.example.domain.UserVO;
import org.example.service.ChatMessageService;
import org.example.service.ChatParticipateSerivce;
import org.example.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	
	@Autowired
	private ChatMessageService chatMessageService; 
	
	@Autowired
	private ChatParticipateSerivce chatParticipateSerivce; 
	
	// json형식으로 리스트 데이터를 전송
	@GetMapping(value = "/myChatRoom/{id}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<ChatRoomVO>> getMyChatRoom(@PathVariable("id") String id){
		List<ChatRoomVO> myRoomList = chatService.getMyList(id);
		return new ResponseEntity<List<ChatRoomVO>>(myRoomList, HttpStatus.OK);
	}
	
	@RequestMapping(method = {RequestMethod.PUT,RequestMethod.PATCH},value = "/updateInParticipate", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> updateInParticipate(@RequestBody ChatParticipateVO participateVO) {
		int chnum = participateVO.getChnum();
		String id = participateVO.getId();
		log.info(participateVO);
		int _result = chatParticipateSerivce.updateInChatParticipateVO(chnum, id);
		String result = _result == 1 ? "success" : "fail";
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}
	
	@RequestMapping(method = {RequestMethod.PUT,RequestMethod.PATCH},value = "/updateOutParticipate", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> updateOutParticipate(@RequestBody ChatParticipateVO participateVO) {
		int chnum = participateVO.getChnum();
		String id = participateVO.getId();
		log.info(participateVO);
		int _result = chatParticipateSerivce.updateOutChatParticipateVO(chnum, id);
		String result = _result == 1 ? "success" : "fail";
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}
	
	@GetMapping(value = "/getChatParticipateList/{chnum}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<ChatParticipateVO>> getChatParticipateList(@PathVariable("chnum") int chnum){
		List<ChatParticipateVO> ChatParticipateList = chatParticipateSerivce.getChatParticipateVOByChnum(chnum);
		return new ResponseEntity<List<ChatParticipateVO>>(ChatParticipateList, HttpStatus.OK);
	}
	 
	@GetMapping(value = "/allChatRoom/{id}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<ChatUserCurrentState>> getAllChatRooms(@PathVariable("id") String id){
		List<ChatUserCurrentState> allRoomList = chatService.getAllList(id);
		return new ResponseEntity<List<ChatUserCurrentState>>(allRoomList, HttpStatus.OK);
	}
	
	// 채팅방 삭제
	@DeleteMapping(value = "/delete/{chnum}", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> deleteRoom(@PathVariable("chnum") int chnum){
		int _result = chatService.deleteRoom(chnum);
		String result = _result == 1 ? "success": "fail"; 
		return new ResponseEntity<String> (result,HttpStatus.OK);
	}

	// 채팅방 status를 0으로 변경
	@RequestMapping(method = {RequestMethod.PUT,RequestMethod.PATCH},value = "/unable/{chnum}", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> unableRoom(@PathVariable("chnum") int chnum) {
		int _result = chatService.unableRoom(chnum);
		String result = _result == 1 ? "success" : "fail";
		return new ResponseEntity<String>(result, HttpStatus.OK);
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
	
	@GetMapping(value = "/getUserById/{id}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<UserVO> getUserById(@PathVariable("id") String id){
		UserVO user = chatService.getUserById(id);
		return new ResponseEntity<UserVO>(user, HttpStatus.OK);
	}
	
	// 채팅방 입장 요청 거절 시 
	@RequestMapping(method = { RequestMethod.PUT,RequestMethod.PATCH }, 
			value = "/updateValidate", consumes = "application/json" , produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> updateValidate(@RequestBody ChatUserValidateVO chatUserValidateVO){
		chatService.updateRequest(chatUserValidateVO);
		return new ResponseEntity<String>("", HttpStatus.OK);
	}
	
	// 채팅방 입장 요청 수락 시
	@RequestMapping(method = { RequestMethod.PUT,RequestMethod.PATCH }, 
			value = "/requestApproval", consumes = "application/json" , produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> requestApproval(@RequestBody ChatUserValidateVO chatUserValidateVO){
		int _result = chatService.requestApproval(chatUserValidateVO);
		String result = "fail";
		if(_result == 1) {
			result = "success";
		}
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}
	
	@PostMapping(value = "/makeParticipate", consumes = "application/json", produces = { MediaType.TEXT_PLAIN_VALUE })
	public ResponseEntity<String> makeParticipateObj(@RequestBody ChatParticipateVO participateVO) {
		int result = chatParticipateSerivce.insertChatParticipateVO(participateVO);
		return result == 1 ? new ResponseEntity<String>("success", HttpStatus.OK)
				: new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	// ChatRoomVO에 userid와 chnum이 있으므로 그냥 이것으로 받음
	@RequestMapping(method = { RequestMethod.PUT,RequestMethod.PATCH }, 
			value = "/updateUserId", consumes = "application/json" , produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> updateUserId(@RequestBody ChatRoomVO chatRoomVO){
		chatService.updateUserId(chatRoomVO);
		log.info(chatRoomVO.getUserid() + "," + chatRoomVO.getChnum());
		return new ResponseEntity<String>("", HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/deleteValidate", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> deleteValidate(@RequestBody ChatUserValidateVO chatUserValidateVO){
		int _result = chatService.deleteRequest(chatUserValidateVO);
		String result = null;
		if(_result == 1) {
			result = "success";
		}else if(_result == 2) {
			result = "fail"; // 이미 승인된 요청
		}else {
			result = "error";
		}
		return new ResponseEntity<String> (result,HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/outRoomRequest", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> outRoomRequest(@RequestBody ChatUserValidateVO chatUserValidateVO){
		int _result = chatService.outRoomRequest(chatUserValidateVO);
		String result = null;
		if(_result == 2) {
			result = "deleteRoom"; // 방 삭제
		}else if(_result == 1) {
			result = "success";
		}else {
			result = "fail";
		}
		return new ResponseEntity<String> (result,HttpStatus.OK);
	}
	// id에 해당하는 사용자의 입장일로부터 시작된 대화를 불러와야한다. 그래서 id와 chnum을 받아왔다.
	@GetMapping(value = "/getMessage/{chnum}/{id}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE,MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<ChatMessageVO>> getMyRoomRequest(@PathVariable("chnum") int chnum, @PathVariable("id") String id){
		// 특정 채팅방의 대화 목록을 가져옴
		List<ChatMessageVO> messageList = chatMessageService.getAllChatMessage(id, chnum);
		return new ResponseEntity<List<ChatMessageVO>>(messageList, HttpStatus.OK);
	}
	
	@PostMapping(value = "/getUnReadChatCount", consumes = "application/json", produces = { MediaType.TEXT_PLAIN_VALUE })
	public String getUnReadChatCount(@RequestBody ChatRoomVO chatVO) {
		log.info("chatVO 결과 : " + chatVO);
		int result = chatMessageService.getUnReadChatCount(chatVO.getChnum(), chatVO.getId());
		log.info("getUnReadChatCount 결과 : " + result);
		return String.valueOf(result);
	}
	
	
	@GetMapping(value = "/getUnReadChatMessage/{chnum}/{id}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE,MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<ChatMessageVO>> getUnReadChatMessage(@PathVariable("chnum") int chnum, @PathVariable("id") String id){
		// 특정 채팅방의 대화 목록을 가져옴
		List<ChatMessageVO> unReadMessageList = chatMessageService.getUnReadChatMessage(chnum, id);
		log.info("unReadMessageList: "+unReadMessageList);
		return new ResponseEntity<List<ChatMessageVO>>(unReadMessageList, HttpStatus.OK);
	}
	
	@GetMapping(value = "/getReadChatMessage/{chnum}/{id}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE,MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<ChatMessageVO>> getReadChatMessage(@PathVariable("chnum") int chnum, @PathVariable("id") String id){
		// 특정 채팅방의 대화 목록을 가져옴
		List<ChatMessageVO> readMessageList = chatMessageService.getReadChatMessage(chnum, id);
		log.info("readMessageList: "+readMessageList);
		return new ResponseEntity<List<ChatMessageVO>>(readMessageList, HttpStatus.OK);
	}
	
}
