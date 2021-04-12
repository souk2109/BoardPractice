package org.example.service;

import java.util.List;

import org.example.domain.ChatMessageVO;

public interface ChatMessageService {
	// 특정 채널 명에 해당하는 모든 메세지를 가지고옴 (승인 요청 받은 그 이후로의 메세지)
	public List<ChatMessageVO> getAllChatMessage(String id, int chnum);

	// 특정 채널 명에 메세지를 추가함
	public int insertChatMessage(ChatMessageVO chatMessageVO);

	// 특정 id가 보낸 메세지를 모두 삭제함
	public int deleteChatMessageById(String id);
}
