package org.example.service;

import org.example.domain.UserVO;
import org.example.mapper.AuthMapper;
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
public class UserTest {
	@Autowired
	private UserService service;
	
	@Autowired
	private AuthMapper mapper;
	
	@Test
	public void insertTest() {
		UserVO userVO = new UserVO();
		userVO.setId("user3");
		userVO.setPwd("1234");
		userVO.setNickname("연습용");
		int result = service.registerUser(userVO);
		log.info(result);
	}
	@Test
	public void addAuthentication() {
		int result = mapper.insertAuthMember("user3");
		log.info(result);
	}
}
