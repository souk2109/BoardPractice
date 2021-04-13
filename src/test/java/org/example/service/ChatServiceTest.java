package org.example.service;

import java.util.ArrayList;
import java.util.List;

import org.example.domain.ChatMyRoomRequestVO;
import org.example.domain.ChatRoomVO;
import org.example.mapper.ChatMapperTest;
import org.example.mapper.ChatRoomMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "file:src/main/webapp/WEB-INF/spring/root-context.xml", "file:src/main/webapp/WEB-INF/spring/security-context.xml" })
@Log4j
public class ChatServiceTest {
	@Autowired
	private ChatService chatService;

	@Autowired
	private ChatRoomMapper chatRoomMapper;
	/*
	 * @Test public void insertTest() { ChatRoomVO chatRoomVO = new ChatRoomVO();
	 * chatRoomVO.setId("user5"); chatRoomVO.setHostNick("스프링러");
	 * chatRoomVO.setRoomNick("스프링 할사람"); chatRoomVO.setMaxNum(5); int result =
	 * chatService.makeChatRoom(chatRoomVO); log.info("결과는 : "+result); }
	 */

	@Test
	public void getListTest() {
		List<ChatRoomVO> list = chatService.getMyList("user2");
		list.forEach(room -> {
			log.info(room);
		});
	}
	/*
	 * @Test public void updateValidateTest() { ChatRoomVO chatRoomVO = new
	 * ChatRoomVO(); chatRoomVO.setId("user3"); chatRoomVO.setHostNick("연습이용");
	 * chatRoomVO.setRoomNick("연습용"); chatRoomVO.setMaxNum(10);
	 * chatService.makeChatRoom(chatRoomVO); }
	 * 
	 * @Test public void getMyRoomRequestTest() { List<ChatMyRoomRequestVO> list =
	 * chatService.getMyRoomRequests("user2"); list.forEach(room -> {
	 * log.info(room); }); }
	 */

	/*
	 * @Test public void userIdTest() { String result = chatService.getUserId(6817);
	 * System.out.println(result); }
	 */
	@Test
	public void test() {
		List<ChatMyRoomRequestVO> requestList = chatRoomMapper.getMyRoomRequests("user2");
		List<ChatMyRoomRequestVO> requestToRemove = new ArrayList<ChatMyRoomRequestVO>();
		requestList.forEach(request -> {
			// 요청을 한 방의 status가 0이면
			if (!request.isStatus()) {
				requestToRemove.add(request);
			}
		});
		requestToRemove.forEach(request -> {
			requestList.remove(request);
		});
	}
 
}
