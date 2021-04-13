package org.example.domain;

import java.util.Date;

import lombok.Data;

/*  
          현재 접속한 사용자의 채팅방 입장 현황을 알기 위한 객체 (아래 sql문의 결과를 받음)
	select t1.hostNick, t1.roomNick, t1.maxNum, t2.validate, t1.regdate, t2.requestdate, t2.updatedate
	from tbl_chat_room t1
	left join (select * from tbl_chat_validate where id='user2') t2
	on  t1.chnum = t2.chnum;
*/
@Data
public class ChatUserCurrentState {
	private String hostId;
	private String chnum;
	private String hostNick;
	private String roomNick;
	private boolean status;
	private int currentNum;
	private int maxNum;
	private int validate;
	private Date regdate;
	private Date requestdate;
	private Date updatedate;
}
