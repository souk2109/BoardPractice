package org.example.service;

import java.util.List;

import org.example.domain.ChatMyRoomRequestVO;
import org.example.domain.ChatRoomVO;
import org.example.domain.ChatUserCurrentState;
import org.example.domain.ChatUserValidateVO;

public interface ChatService {
	public int makeChatRoom(ChatRoomVO chatRoomVO);
	public List<ChatRoomVO> getMyList(String id);
	public List<ChatUserCurrentState> getAllList(String id);
	public int deleteRoom(int chnum);
	public int updateRoom(ChatRoomVO chatRoomVO);
	public int joinRequest(ChatUserValidateVO userValidateVO);
	public List<ChatMyRoomRequestVO> getMyRoomRequests(String id);
}
