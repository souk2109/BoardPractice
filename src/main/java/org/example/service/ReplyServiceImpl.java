package org.example.service;

import java.util.List;

import org.example.domain.Criteria;
import org.example.domain.ReplyVO;
import org.example.mapper.ReplyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class ReplyServiceImpl implements ReplyService{
	@Autowired
	private ReplyMapper replyMapper;

	@Override
	public int register(ReplyVO replyVO) {
		log.info("댓글 등록 :" + replyVO);
		return replyMapper.insert(replyVO);
	}

	@Override
	public ReplyVO get(int rno) {
		log.info(rno+"번 댓글 조회:");
		return replyMapper.get(rno);
	}

	@Override
	public int modify(ReplyVO replyVO) {
		log.info(replyVO.getRno()+"번 댓글 수정");
		return replyMapper.update(replyVO);
	}

	@Override
	public int remove(int rno) {
		log.info(rno+"번 댓글 삭제");
		return replyMapper.remove(rno);
	}

	@Override
	public List<ReplyVO> getList(int bno, Criteria criteria) {
		log.info("댓글 리스트 불러오기!");
		return replyMapper.getListWithPaging(bno, criteria);
	}
	
}
