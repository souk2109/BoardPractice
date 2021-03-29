package org.example.service;

import java.util.List;

import org.example.domain.Criteria;
import org.example.domain.ReplyPageDTO;
import org.example.domain.ReplyVO;

public interface ReplyService {
	public int register(ReplyVO replyVO);
	public ReplyVO get(int rno);
	public int modify(ReplyVO replyVO);
	public int remove(int rno);
	public List<ReplyVO> getList(int bno, Criteria criteria);
	public int getTotal(int bno);
	public ReplyPageDTO getListPage(Criteria criteria, int bno);
	
}
