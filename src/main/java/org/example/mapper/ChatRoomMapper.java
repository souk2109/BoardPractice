package org.example.mapper;

import java.util.List;

import org.example.domain.ChatRoomVO;

public interface ChatRoomMapper {
	public int insert(ChatRoomVO chatRoomVO);
	public int pkCheck(int chnum);
	public List<ChatRoomVO> getList(String id);
	public List<ChatRoomVO> getAllList();
	public int delete(int chnum);
	public int update(ChatRoomVO chatRoomVO);
	public int joinRequest(ChatRoomVO chatRoomVO);
}
