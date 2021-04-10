package org.example.service;

import java.util.List;

import org.example.domain.ChatMessageVO;
import org.example.mapper.ChatMessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatMessageServiceImpl implements ChatMessageService{
	@Autowired
	private ChatMessageMapper chatMessageMapper;
	@Override
	public List<ChatMessageVO> getAllChatMessage(int chnum) {
		return chatMessageMapper.getAllChatMessage(chnum);
	}

	@Override
	public int insertChatMessage(ChatMessageVO chatMessageVO) {
		return chatMessageMapper.insertChatMessage(chatMessageVO);
	}

	@Override
	public int deleteChatMessageById(String id) {
		return chatMessageMapper.deleteChatMessageById(id);
	}

}
