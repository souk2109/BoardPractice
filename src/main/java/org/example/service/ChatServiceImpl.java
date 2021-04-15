package org.example.service;

import java.util.ArrayList;
import java.util.List;
 
import org.example.domain.ChatMyRoomRequestVO;
import org.example.domain.ChatRoomVO;
import org.example.domain.ChatUserCurrentState;
import org.example.domain.ChatUserValidateVO;
import org.example.domain.UserVO;
import org.example.mapper.ChatRoomMapper;
import org.example.mapper.ChatValidateMapper;
import org.example.mapper.UserMapper;
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
	
	@Autowired
	private UserMapper userMapper;

	
	@Transactional
	@Override
	public int makeChatRoom(ChatRoomVO chatRoomVO) {
		int chnum = makeRandomNum(); // 방 번호 생성
		String id = chatRoomVO.getId();
		chatRoomVO.setChnum(chnum);
		chatRoomVO.setUserid(id);
		chatRoomMapper.insert(chatRoomVO);
		chatValidateMapper.insertValidate(chnum, id, 2);
		return chnum;
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
	// status를 0으로 변경
	public int unableRoom(int chnum) {
		return chatRoomMapper.unable(chnum);
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

	@Transactional
	@Override
	public List<ChatMyRoomRequestVO> getMyRoomRequests(String id) {
		List<ChatMyRoomRequestVO> requestList = chatRoomMapper.getMyRoomRequests(id);
		List<ChatMyRoomRequestVO> requestToRemove = new ArrayList<ChatMyRoomRequestVO>(); // 삭제할 request를 담음
		requestList.forEach(request ->{
			// 요청을 한 방의 status가 0이면
			if(!request.isStatus()) {
				// 해당 request를 삭제(mapper를 이용)
				ChatUserValidateVO chatUserValidateVO = new ChatUserValidateVO();
				chatUserValidateVO.setId(id);
				chatUserValidateVO.setChnum(request.getChnum());
				chatValidateMapper.deleteValidate(chatUserValidateVO);
				request.getChnum();
				// 리스트에서도 삭제
				requestToRemove.add(request);
			}
		});
		requestToRemove.forEach(request -> {
			requestList.remove(request);
		});
		return requestList;
	}

	@Override
	public int updateRequest(ChatUserValidateVO userValidateVO) {
		return chatValidateMapper.updateValidate(userValidateVO);
	}

	// 순수하게 요청을 삭제한다.(1반환)
	// 이미 참여중인 경우에는 삭제를 못하게 한다(2반환).
	@Override
	public int deleteRequest(ChatUserValidateVO chatUserValidateVO) {
		// 해당 유저의 validate를 가져옴
		int validate = chatValidateMapper.getValidate(chatUserValidateVO.getChnum(), chatUserValidateVO.getId());
		// 이미 참여중인 경우
		if(validate == 4) {
			return 2;
		}else {
			return chatValidateMapper.deleteValidate(chatUserValidateVO);	
		}
	}
	
	// 방을 나갔을 때 인원 수를 1줄이고 인원수가 0인경우 방을 삭제한다. 0이 아니라면 validate만 삭제한다.
	@Transactional
	@Override
	public int outRoomRequest(ChatUserValidateVO chatUserValidateVO) {
		int chnum = chatUserValidateVO.getChnum();
		chatRoomMapper.minusCurrentNum(chnum);
		if(chatRoomMapper.getCurrentNum(chnum)==0) {
			chatRoomMapper.delete(chnum);
			log.info(chnum + "방, 삭제 완료");
			return 2;
		}
		return chatValidateMapper.deleteValidate(chatUserValidateVO);
	}
	@Override
	public String getUserId(int chnum) {
		return chatRoomMapper.getUserId(chnum);
	}

	@Override
	public void updateUserId(ChatRoomVO chatRoomVO) {
		chatRoomMapper.updateUserId(chatRoomVO);
	}

	// 채팅방 참여 요청에 승인하는 메소드
	// 1.승인을 할 때 신청자가 요청을 철회했는지 체크
	// 2. 철회하지 않았다면 db에 등록 후 숫자를 1증가
	@Transactional
	@Override
	public int requestApproval(ChatUserValidateVO chatUserValidateVO) {
		log.info("들어온것: "+chatUserValidateVO);
		try {
			Integer validate = chatValidateMapper.getValidate(chatUserValidateVO.getChnum(), chatUserValidateVO.getId());
			if(validate == 1) {
				chatRoomMapper.addCurrentNum(chatUserValidateVO.getChnum());
				return chatValidateMapper.requestApproval(chatUserValidateVO);
			}
			return 2;
		} catch (NullPointerException e) {
			log.info("요청이 없습니다.(NPE에러 발생)");
			return 2;
		}
		 
	}
 

	// chnum인 채팅방에 있는 사용자 명을 받아옴
	// getUserNicknameByChnum로 수정하기
	@Override
	public List<String> getUserNicknameByChnum(int chnum) {
		List<String> list = chatRoomMapper.getUserNicknameByChnum(chnum);
		return list;
	}

	@Override
	public UserVO getUserById(String id) {
		return userMapper.getUserById(id);
	}
}
