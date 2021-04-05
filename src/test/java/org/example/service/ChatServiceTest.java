package org.example.service;

import org.example.domain.ChatRoomVO;
import org.example.mapper.ChatMapperTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.log4j.Log4j;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml",
	"file:src/main/webapp/WEB-INF/spring/security-context.xml"})
@Log4j
public class ChatServiceTest {
	@Autowired
	private ChatService chatService;
	
	@Test
	public void insertTest() {
		ChatRoomVO chatRoomVO = new ChatRoomVO();
		chatRoomVO.setId("user5");
		chatRoomVO.setHostNick("스프링러");
		chatRoomVO.setRoomNick("스프링 할사람");
		chatRoomVO.setMaxNum(5);
		int result = chatService.makeChatRoom(chatRoomVO);
		log.info("결과는 : "+result);
	}
}
