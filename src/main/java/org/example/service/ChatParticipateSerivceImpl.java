package org.example.service;

import java.util.List;

import org.example.domain.ChatParticipateVO;
import org.example.mapper.ChatParticipateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatParticipateSerivceImpl implements ChatParticipateSerivce{
	@Autowired
	private ChatParticipateMapper participateMapper;

	@Override
	public List<ChatParticipateVO> getChatParticipateVOByChnum(int chnum) {
		return participateMapper.getChatParticipateVOByChnum(chnum);
	}

	@Override
	public ChatParticipateVO getChatParticipateVOByChnumAndId(int chnum, String id) {
		return participateMapper.getChatParticipateVOByChnumAndId(chnum, id);
	}

	@Override
	public int insertChatParticipateVO(ChatParticipateVO vo) {
		return participateMapper.insertChatParticipateVO(vo);
	}

	@Override
	public int updateInChatParticipateVO(int chnum, String id) {
		return participateMapper.updateInChatParticipateVO(chnum, id);
	}

	@Override
	public int updateOutChatParticipateVO(int chnum, String id) {
		return participateMapper.updateOutChatParticipateVO(chnum, id);
	}
}
