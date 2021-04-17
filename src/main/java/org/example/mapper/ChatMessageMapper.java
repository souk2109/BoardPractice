package org.example.mapper;
 
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.example.domain.ChatMessageVO;

// tbl_chat_message 테이블을 다룰 mapper
public interface ChatMessageMapper {
	// 특정 채널 명에 해당하는 모든 메세지를 가지고 옴
	public List<ChatMessageVO> getAllChatMessage(@Param("chnum") int chnum, @Param("approvalDate") String approvalDate);
	
	// 아직 읽지 않은 메세지들을 가지고 옴
	public List<ChatMessageVO> getUnReadChatMessage(@Param("chnum") int chnum, @Param("id") String id);
	
	// 이미 읽은 메세지들을 가지고 옴
	public List<ChatMessageVO> getReadChatMessage(@Param("chnum") int chnum, @Param("id") String id);
	 
	// 아직 읽지 않은 메세지의 수를 가지고 옴
	public int getUnReadChatCount(@Param("chnum") int chnum, @Param("id") String id);
	// 특정 채널 명에 메세지를 추가함
	public int insertChatMessage(ChatMessageVO chatMessageVO);
	
	// 특정 id가 보낸 메세지를 모두 삭제함
	public int deleteChatMessageById(String id);
}
