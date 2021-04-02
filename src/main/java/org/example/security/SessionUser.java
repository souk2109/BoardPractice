package org.example.security;


import java.util.stream.Collectors;

import org.example.domain.UserVO;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// @Data를 사용하려고 헀지만 constructor 도중 에러가 생길 수 있음
@Getter
@Setter
@ToString
public class SessionUser extends User{
	private static final long serialVersionUID = 1L;
	// id, pwd, enabled 외 사용할 정보 등록
	private String nickname;

	public SessionUser(UserVO user) {
		super(user.getId(), user.getPwd(),
			user.getAuthList().stream().map(auth -> new SimpleGrantedAuthority(auth.getAuth())).collect(Collectors.toList()));
		this.nickname = user.getNickname();
	}
}
