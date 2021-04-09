package org.example.mapper;

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
public class ChatMapperTest {
	@Autowired
	private ChatRoomMapper chatRoomMapper;
	
	@Autowired
	private ChatValidateMapper chatValidateMapper;
	/*
	 * @Test public void innsertTest() { ChatRoomVO chatRoomVO = new ChatRoomVO();
	 * 
	 * chatRoomVO.setId("user5"); int chnum = (int)(Math.random()*10000)+1; // 1부터
	 * 10,000까지 chatRoomVO.setChnum(chnum); chatRoomVO.setHostNick("스프링러");
	 * chatRoomVO.setRoomNick("스프링 할사람"); chatRoomVO.setMaxNum(5);
	 * chatRoomMapper.insert(chatRoomVO); }
	 */
	
	@Test
	public void getTest() {
		List<ChatUserCurrentState> list = chatRoomMapper.getAllList("user2");
		list.forEach(room -> {
			log.info(room);
			});
	}
	
	@Test
	public void getUserId() {
		String list = chatRoomMapper.getUserId(2248);
		System.out.println(list);
	}
}
