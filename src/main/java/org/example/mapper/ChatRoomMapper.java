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
	
	public int getCurrentNum(int chnum);
	public int addCurrentNum(int chnum); // 현재 인원을 증가
	public int minusCurrentNum(int chnum); // 현재 인원을 감소
	public String getUserId(int chnum); // 채팅방에 저장된 사용자의 id를 불러옴(userid)
	public void updateUserId(ChatRoomVO chatRoomVO);
	public int unable(int chnum);
	public List<String> getUserNicknameByChnum(int chnum);
}
