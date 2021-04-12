package org.example.mapper;
 
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.example.domain.ChatMessageVO;

// tbl_chat_message 테이블을 다룰 mapper
public interface ChatMessageMapper {
	// 특정 채널 명에 해당하는 모든 메세지를 가지고옴
	public List<ChatMessageVO> getAllChatMessage(@Param("chnum") int chnum, @Param("approvalDate") String approvalDate);
	
	// 특정 채널 명에 메세지를 추가함
	public int insertChatMessage(ChatMessageVO chatMessageVO);
	
	// 특정 id가 보낸 메세지를 모두 삭제함
	public int deleteChatMessageById(String id);
}
