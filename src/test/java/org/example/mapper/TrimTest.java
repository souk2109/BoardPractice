package org.example.mapper;

import org.example.domain.Criteria;
import org.example.service.BoardServiceTest;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class TrimTest {
	
	@Autowired
	private BoardMapper mapper;
	
	@org.junit.Test
	public void Test() {
		Criteria criteria = new Criteria(1, 10);
		criteria.setKeyword("ee");
		criteria.setType("W");
		log.info("갯수: "+mapper.getCount(criteria));
		mapper.getListWithPaging(criteria).forEach(board -> {log.info(board);});;
	}
}
