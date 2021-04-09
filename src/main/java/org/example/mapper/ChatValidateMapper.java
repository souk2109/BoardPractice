package org.example.mapper;

import org.apache.ibatis.annotations.Param;
import org.example.domain.ChatUserValidateVO;

public interface ChatValidateMapper {
	public int updateValidate(ChatUserValidateVO validateVO);
	public int insertValidate(@Param("chnum") int chnum, @Param("id") String id, @Param("validate") int validate);
	public int deleteValidate(ChatUserValidateVO chatUserValidateVO);
}
