package org.example.service;

import java.util.List;

import org.example.domain.ChatMyRoomRequestVO;
import org.example.domain.ChatRoomVO;
import org.example.domain.ChatUserCurrentState;
import org.example.domain.ChatUserValidateVO;
import org.example.domain.UserVO;

public interface ChatService {
	public int makeChatRoom(ChatRoomVO chatRoomVO);
	public List<ChatRoomVO> getMyList(String id);
	public List<ChatUserCurrentState> getAllList(String id);
	public int deleteRoom(int chnum);
	public int unableRoom(int chnum);
	public int updateRoom(ChatRoomVO chatRoomVO);
	public String getUserId(int chnum);
	public int outRoomRequest(ChatUserValidateVO chatUserValidateVO);
	
	public int joinRequest(ChatUserValidateVO userValidateVO);
	public int updateRequest(ChatUserValidateVO userValidateVO);
	public List<ChatMyRoomRequestVO> getMyRoomRequests(String id);
	public int deleteRequest(ChatUserValidateVO chatUserValidateVO);
	// public int addCurrentNum(int chnum);
	public void updateUserId(ChatRoomVO chatRoomVO);
	public int requestApproval(ChatUserValidateVO chatUserValidateVO);
	public UserVO getUserById(String id);
}
