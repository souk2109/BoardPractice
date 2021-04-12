package org.example.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.example.domain.ChatMyRoomRequestVO;
import org.example.domain.ChatRoomVO;
import org.example.domain.ChatUserCurrentState;
import org.example.mapper.ChatMessageMapper;
import org.example.mapper.ChatRoomMapper;
import org.example.mapper.ChatValidateMapper;
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
public class ChatMessageServiceTest {
	
	@Autowired
	private ChatMessageService chatService;
	
	@Test
	public void test() {
	
		chatService.getAllChatMessage("user3", 8084);
	}
}
