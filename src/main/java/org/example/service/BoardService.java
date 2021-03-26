package org.example.service;

import java.util.List;

import org.example.domain.BoardVO;
import org.example.domain.Criteria;

public interface BoardService {
	public void register(BoardVO boardVO);
	public BoardVO get(int bno);
	public boolean modify(BoardVO boardVO);
	public boolean remove(int bno);
	public List<BoardVO> getList(Criteria criteria);
	public int getCount(Criteria criteria);
}
