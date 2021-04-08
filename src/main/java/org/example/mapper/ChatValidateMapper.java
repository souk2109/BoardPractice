package org.example.mapper;

import org.apache.ibatis.annotations.Param;

public interface ChatValidateMapper {
	public int updateValidate(@Param("chnum") int chnum, @Param("id") String id);
	public int insertValidate(@Param("chnum") int chnum, @Param("id") String id, @Param("validate") int validate);
}
