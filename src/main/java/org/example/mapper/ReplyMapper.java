package org.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.example.domain.Criteria;
import org.example.domain.ReplyVO;

public interface ReplyMapper {
	public int insert(ReplyVO replyVO);
	public ReplyVO get(int rno);
	public int remove(int rno);
	public int update(ReplyVO replyVO);
	public List<ReplyVO> getListWithPaging(@Param("bno")int bno,@Param("criteria") Criteria criteria);
}
