package org.example.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.example.domain.ChatParticipateVO;

public interface ChatParticipateSerivce {
		// chnum으로 모든 사용자의 채팅방 참여 현황을 가져옴
		public List<ChatParticipateVO> getChatParticipateVOByChnum(int chnum);
		
		// chnum과 id로 모든 사용자의 채팅방 참여 현황을 가져옴
		public ChatParticipateVO getChatParticipateVOByChnumAndId(@Param("chnum") int chnum, @Param("id")String id);
		
		// vo객체로 사용자의 참여 현황을 추가
		public int insertChatParticipateVO(ChatParticipateVO vo);
		
		//chnum과 id로  사용자가 참여했을 떄 업데이트
		public int updateInChatParticipateVO(@Param("chnum") int chnum, @Param("id")String id);
		
		//chnum과 id로  사용자가 나갔을 떄 업데이트
		public int updateOutChatParticipateVO(@Param("chnum") int chnum, @Param("id")String id);
}
