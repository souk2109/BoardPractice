package org.example.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.example.domain.ChatMessageVO;
import org.example.mapper.ChatMessageMapper;
import org.example.mapper.ChatValidateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.log4j.Log4j;

@Log4j
@Service
public class ChatMessageServiceImpl implements ChatMessageService{
	@Autowired
	private ChatMessageMapper chatMessageMapper;
	
	@Autowired
	private ChatValidateMapper chatValidateMapper;
	
	@Transactional
	@Override
	public List<ChatMessageVO> getAllChatMessage(String id, int chnum) {
		Date _approvalDate = chatValidateMapper.getApprovalDate(chnum, id);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		String approvalDate = format.format(_approvalDate);
		return chatMessageMapper.getAllChatMessage(chnum, approvalDate);
	}

	@Override
	public int insertChatMessage(ChatMessageVO chatMessageVO) {
		return chatMessageMapper.insertChatMessage(chatMessageVO);
	}

	@Override
	public int deleteChatMessageById(String id) {
		return chatMessageMapper.deleteChatMessageById(id);
	}

	@Override
	public List<ChatMessageVO> getUnReadChatMessage(int chnum, String id) {
		return chatMessageMapper.getUnReadChatMessage(chnum, id);
	}

	@Override
	public List<ChatMessageVO> getReadChatMessage(int chnum, String id) {
		return chatMessageMapper.getReadChatMessage(chnum, id);
	}

	@Override
	public int getUnReadChatCount(int chnum, String id) {
		return chatMessageMapper.getUnReadChatCount(chnum, id);
	}

}
