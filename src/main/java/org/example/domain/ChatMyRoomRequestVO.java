package org.example.domain;

import java.util.Date;

import lombok.Data;

// 내가 만든 방에 신청한 요청들에 대한 정볼륻 담음
@Data
public class ChatMyRoomRequestVO {
	private String userid;
	private String roomnick;
	private int chnum;
	private int validate;
	private Date requestdate;
	private boolean status;
}
