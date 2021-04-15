package org.example.service;

import org.example.domain.UserVO;
import org.example.mapper.AuthMapper;
import org.example.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class UserServiceImpl implements UserService{
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private AuthMapper authMapper;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Transactional
	@Override
	public int registerUser(UserVO userVO) {
		String rawPassword = userVO.getPwd();
		String encodedPassword = encoder.encode(rawPassword);
		userVO.setPwd(encodedPassword);
		log.info("암호화된 비밀번호 : " + encodedPassword);
		int result = userMapper.insertUser(userVO);
		authMapper.insertAuthUser(userVO.getId());
		return result;
	}
}
