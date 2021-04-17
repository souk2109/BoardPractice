package org.example.mapper;
import org.example.domain.ChatParticipateVO;
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

public class ChatParticipateMapperTest {
	@Autowired
	private ChatParticipateMapper chatParticipateMapper;
	
	@Test
	public void getTest() {
		chatParticipateMapper.getChatParticipateVOByChnum(2);
		//chatParticipateMapper.getChatParticipateVOByChnumAndId(2753, "user3");
	}
	
	@Test
	public void insertTest() {
		ChatParticipateVO vo = new ChatParticipateVO();
		vo.setChnum(2753);
		vo.setId("user2");
		vo.setNickname("유저3");
		
		chatParticipateMapper.insertChatParticipateVO(vo);
	}
	
	@Test
	public void updateInTest() {
		chatParticipateMapper.updateInChatParticipateVO(2753, "user3");
	}
	
	@Test
	public void updateOutTest() {
		chatParticipateMapper.updateOutChatParticipateVO(2753, "user3");
	}
	
}
