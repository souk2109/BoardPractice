package org.example.service;

import java.util.List;

import org.example.domain.ChatRoomVO;

public interface ChatService {
	public int makeChatRoom(ChatRoomVO chatRoomVO);
	public List<ChatRoomVO> getMyList(String id);
}
