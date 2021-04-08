package org.example.mapper;

import java.util.List;

import org.example.domain.ChatMyRoomRequestVO;
import org.example.domain.ChatRoomVO;
import org.example.domain.ChatUserCurrentState;
import org.example.domain.ChatUserValidateVO;

public interface ChatRoomMapper {
	public int insert(ChatRoomVO chatRoomVO);
	public int pkCheck(int chnum);
	public List<ChatRoomVO> getList(String id);
	public List<ChatUserCurrentState> getAllList(String id);
	public int delete(int chnum);
	public int update(ChatRoomVO chatRoomVO);
	public int joinRequest(ChatUserValidateVO userValidateVO) throws Exception;
	public List<ChatMyRoomRequestVO> getMyRoomRequests(String id);
}
