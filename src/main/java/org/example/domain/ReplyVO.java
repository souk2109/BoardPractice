package org.example.domain;

import java.sql.Date;

import lombok.Data;

// 댓글처리를 위한 vo객체 생성
@Data
public class ReplyVO {
	private int rno;
	private int bno;
	private String reply;
	private String replyer;
	private Date replydate;
	private Date updatedate;
}
