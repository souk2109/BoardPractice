package org.example.domain;

import java.util.Date;
import java.util.List;
 

import lombok.Data;
@Data
public class UserVO {
	private String id;
	private String pwd;
	private String nickname;
	private boolean enabled;
	private Date regDate;
	private Date updateDate;
	
	private List<AuthVO> authList;
	 
}
