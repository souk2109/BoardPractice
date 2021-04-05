package org.example.mapper;

import org.example.domain.ChatRoomVO;

public interface ChatRoomMapper {
	public int insert(ChatRoomVO chatRoomVO);
	public int pkCheck(int chnum);
}
