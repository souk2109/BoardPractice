package org.example.mapper;

import java.util.Date;

import org.apache.ibatis.annotations.Param;
import org.example.domain.ChatUserValidateVO;

public interface ChatValidateMapper {
	public Integer getValidate(@Param("chnum") int chnum, @Param("id") String id);
	public int updateValidate(ChatUserValidateVO validateVO);
	public int insertValidate(@Param("chnum") int chnum, @Param("id") String id, @Param("validate") int validate);
	public int deleteValidate(ChatUserValidateVO chatUserValidateVO);
	public int requestApproval(ChatUserValidateVO chatUserValidateVO); // 채팅방 참여 요청을 수락했을 때 테이블에 정보를 변경해준다.
	public Date getApprovalDate(@Param("chnum") int chnum, @Param("id") String id); // 특정 채널명과 id에 해당하는 사용자의 승인날짜를 가져온다.
}
