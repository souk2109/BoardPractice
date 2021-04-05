package org.example.service;

import org.example.domain.ChatRoomVO;
import org.example.mapper.ChatRoomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ChatServiceImpl implements ChatService{
	@Autowired
	private ChatRoomMapper chatRoomMapper;

	@Override
	public int makeChatRoom(ChatRoomVO chatRoomVO) {
		int randnum = makeRandomNum();
		chatRoomVO.setChnum(randnum);
		return chatRoomMapper.insert(chatRoomVO);
	}

	// 채팅방 번호를 random하게 만들어주는데 중복되는 경우 중복안되는 번호를 생성해서 부여함
	private int makeRandomNum() {
		int result;
		while(true) {
			int randNum = (int)(Math.random()*10000)+1;
			int _result = chatRoomMapper.pkCheck(randNum);
			if(_result == 0) {
				result = randNum;
				break;
			}
		}
		return result;
	}
}
