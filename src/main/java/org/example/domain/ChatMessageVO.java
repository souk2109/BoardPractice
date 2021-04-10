package org.example.domain;

import lombok.Data;

@Data
public class ChatMessageVO {
	private int chnum;
	private String sender; // 보내는 사람의 닉네임
	private String message;
	private String sendDate;
	private String id; // 보내는 사람의 id
	private ChatAction action;
}
