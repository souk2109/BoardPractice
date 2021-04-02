package org.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.example.domain.BoardVO;
import org.example.domain.Criteria;

public interface BoardMapper {
	public List<BoardVO> getList();
	public int insertSelectKey(BoardVO boardVO);
	public BoardVO read(int bno);
	public int delete(int bno);
	public int update(BoardVO boardVO);
	
	public List<BoardVO> getListWithPaging(Criteria criteria);
	public int getCount(Criteria criteria);
	
	public int updateReplyCnt(@Param("bno") int bno, @Param("amount") int amount);
}
