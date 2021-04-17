package org.example.mapper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.example.domain.ChatMyRoomRequestVO;
import org.example.domain.ChatRoomVO;
import org.example.domain.ChatUserCurrentState;
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
public class ChatMessageMapperTest {
	
	@Autowired
	private ChatValidateMapper chatValidateMapper;
	
	@Autowired
	private ChatMessageMapper chatMessageMapper; 
	
	@Test
	public void test() {
		Date Jdate = chatValidateMapper.getApprovalDate(321,"user3");
		System.out.println(Jdate);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		String mysqlDate = format.format(Jdate);
		System.out.println(mysqlDate); 
	}
	
	@Test
	public void test2() {
		chatMessageMapper.getUnReadChatCount(6351, "user2");
		chatMessageMapper.getUnReadChatMessage(6351, "user2");
		chatMessageMapper.getReadChatMessage(6351, "user2");
	}
}
