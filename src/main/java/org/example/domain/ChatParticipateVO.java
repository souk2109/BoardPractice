package org.example.domain;

import java.util.Date;

import lombok.Data;

@Data
public class ChatParticipateVO {
	private int chnum;
	private String id;
	private String nickname;
	private boolean enable;
	private Date inDate;
	private Date outDate;
	private int activeTime;
	}
