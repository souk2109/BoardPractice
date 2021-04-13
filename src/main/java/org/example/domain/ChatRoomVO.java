package org.example.domain;

import java.util.Date;

import lombok.Data;
// 채팅방 만들 때 입력받을 내용 : 방장 아이디(id, fk). 방장 닉네임(hostNick), 방 닉네임(roomNick), 
// 					방 번호(chnum,pk), 최대수용인원(maxNum), regDate: 방 생성일

@Data
public class ChatRoomVO {
	private int chnum;
	private String id;
	private String userid;
	private String hostNick;
	private String roomNick;
	private int maxNum;
	private String content;
	private Date regDate;
	private Date updateDate;
	private boolean status;
}
