package org.example.domain;

import lombok.Data;

// 내가 만든 방에 신청한 요청들에 대한 정볼륻 담음
@Data
public class ChatMyRoomRequestVO {
	private String hostid;
	private int chnum;
	private String userid;
	private int validate;
}
