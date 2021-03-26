package sqlTest;

import java.util.List;

import org.example.domain.BoardVO;
import org.example.domain.Criteria;
import org.example.mapper.BoardMapper;
import org.example.service.BoardServiceTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class RownumTest {
	@Autowired
	private BoardMapper mapper;
	
	@Test
	public void testwe() {
		Criteria criteria = new Criteria(1, 10);
		mapper.getListWithPaging(criteria).forEach(board -> log.info(board));
	}
 
	
}
