package org.example.service;

import java.util.List;

import org.example.domain.ChatRoomVO;

public interface ChatService {
	public int makeChatRoom(ChatRoomVO chatRoomVO);
	public List<ChatRoomVO> getMyList(String id);
	public List<ChatRoomVO> getAllList();
	public int deleteRoom(int chnum);
	public int updateRoom(ChatRoomVO chatRoomVO);
	public int joinRequest(ChatRoomVO chatRoomVO);
}
