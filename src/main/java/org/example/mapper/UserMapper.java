package org.example.mapper;

import org.example.domain.UserVO;

public interface UserMapper {
	public UserVO getUser(String id);
	public int insertUser(UserVO userVO);
}
