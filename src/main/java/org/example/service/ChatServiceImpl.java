package org.example.service;

import java.util.List;

import org.example.domain.ChatMyRoomRequestVO;
import org.example.domain.ChatRoomVO;
import org.example.domain.ChatUserCurrentState;
import org.example.domain.ChatUserValidateVO;
import org.example.mapper.ChatRoomMapper;
import org.example.mapper.ChatValidateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.log4j.Log4j;


@Service
@Log4j
public class ChatServiceImpl implements ChatService{
	@Autowired
	private ChatRoomMapper chatRoomMapper;
	
	@Autowired
	private ChatValidateMapper chatValidateMapper;
	
	@Transactional
	@Override
	public int makeChatRoom(ChatRoomVO chatRoomVO) {
		int chnum = makeRandomNum(); // 방 번호 생성
		String id = chatRoomVO.getId();
		chatRoomVO.setChnum(chnum);
		chatRoomMapper.insert(chatRoomVO);
		return chatValidateMapper.insertValidate(chnum, id, 2);
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

	@Override
	public List<ChatRoomVO> getMyList(String id) {
		return chatRoomMapper.getList(id);
	}

	@Override
	public List<ChatUserCurrentState> getAllList(String id) {
		return chatRoomMapper.getAllList(id);
	}

	@Override
	public int deleteRoom(int chnum) {
		return chatRoomMapper.delete(chnum);
	}

	@Override
	public int updateRoom(ChatRoomVO chatRoomVO) {
		return chatRoomMapper.update(chatRoomVO);
	}

	@Override
	public int joinRequest(ChatUserValidateVO userValidateVO) {
		try {
			return chatRoomMapper.joinRequest(userValidateVO);
		} catch (Exception e) {
			log.info("joinRequest ---> pk 중복 에러");
			return 0;
		}
		 
	}

	@Override
	public List<ChatMyRoomRequestVO> getMyRoomRequests(String id) {
		return chatRoomMapper.getMyRoomRequests(id);
	}

	@Override
	public int updateRequest(ChatUserValidateVO userValidateVO) {//updateRequest
		return chatValidateMapper.updateValidate(userValidateVO);
	}
}
