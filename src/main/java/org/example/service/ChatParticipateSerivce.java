package org.example.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.example.domain.ChatParticipateVO;

public interface ChatParticipateSerivce {
		// chnum으로 모든 사용자의 채팅방 참여 현황을 가져옴
		public List<ChatParticipateVO> getChatParticipateVOByChnum(int chnum);
		
		// id에 해당하는 사용자의 enable된 객체를 반환
		public ChatParticipateVO getEnableVO(String id);
		
		// chnum과 id로 모든 사용자의 채팅방 참여 현황을 가져옴
		public ChatParticipateVO getChatParticipateVOByChnumAndId(int chnum, String id);
		
		// vo객체로 사용자의 참여 현황을 추가
		public int insertChatParticipateVO(ChatParticipateVO vo);
		
		//chnum과 id로  사용자가 참여했을 떄 업데이트
		public int updateInChatParticipateVO(int chnum, String id);
		
		//chnum과 id로  사용자가 나갔을 떄 업데이트
		public int updateOutChatParticipateVO(int chnum, String id);
		
		public int deleteChatParticipateVO(int chnum, String id);
}
