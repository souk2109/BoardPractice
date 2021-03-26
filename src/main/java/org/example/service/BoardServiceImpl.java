package org.example.service;

import java.util.List;

import org.example.domain.BoardVO;
import org.example.domain.Criteria;
import org.example.mapper.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
public class BoardServiceImpl implements BoardService{
	
	@Setter(onMethod_ = @Autowired)
	private BoardMapper mapper;
	
	@Override
	public void register(BoardVO boardVO) {
		int result = mapper.insertSelectKey(boardVO);
		log.info("insert result : "+ result);
	}

	@Override
	public BoardVO get(int bno) {
		return mapper.read(bno);
	}

	@Override
	public boolean modify(BoardVO boardVO) {
		return mapper.update(boardVO)==1;
	}

	@Override
	public boolean remove(int bno) {
		return mapper.delete(bno)==1;
	}

	@Override
	public List<BoardVO> getList(Criteria criteria) {
		return mapper.getListWithPaging(criteria);
	}

	@Override
	public int getCount(Criteria criteria) {
		return mapper.getCount(criteria);
	}

}
