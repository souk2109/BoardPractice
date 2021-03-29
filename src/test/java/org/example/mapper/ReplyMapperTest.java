package org.example.mapper;

import java.util.List;

import org.example.domain.BoardVO;
import org.example.domain.Criteria;
import org.example.domain.ReplyVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class ReplyMapperTest {
	
	@Autowired
	private ReplyMapper mapper;
	
	@Test
	public void testMapper() {
		log.info(mapper);
	}
	/* 
	@Test
	public void insertTest() {
		ReplyVO replyVO = new ReplyVO();
		replyVO.setBno(157);
		replyVO.setReply("test");
		replyVO.setReplyer("test");
		log.info(mapper.insert(replyVO));
	}
	
	@Test
	public void getTest() {
		ReplyVO replyVO = new ReplyVO();
		replyVO =mapper.get(1);
		log.info(replyVO);
	}
	
	@Test
	public void removeTest() {
		int result = mapper.remove(1);
		log.info("결과 : "+result);
	}
	@Test
	public void updateTest() {
		ReplyVO replyVO = new ReplyVO();
		replyVO.setRno(2);
		replyVO.setReply("update");
		int result = mapper.update(replyVO);
		log.info(result);
	}
	
	@Test
	public void getListWithPagingTest() {
		Criteria criteria = new Criteria();
		mapper.getListWithPaging(157, criteria);
		//list.forEach(i->{log.info(i);});
	}
	*/
}
