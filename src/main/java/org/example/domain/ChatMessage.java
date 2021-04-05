package org.example.domain;

import lombok.Data;

@Data
public class ChatMessage {
	private String message;
	private String sender;
	private String reciever;
	private int chnum;
	private ChatAction action;
}
