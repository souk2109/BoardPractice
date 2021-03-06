package org.example.domain;

import java.util.Date;

import lombok.Data;

// 사용자가 채팅방에 참여 요청을 할 때 데이터를 담음.
@Data
public class ChatUserValidateVO {
	private int chnum; // 채팅방 번호
	private String id; // 신청한 사람의 아이디
	private int validate;
	private Date requestdate; // 채팅방 참여 신청 날짜
	private Date updatedate; // validate변경 날짜
	private Date approvaldate; // 채팅방 승인 날짜
}
