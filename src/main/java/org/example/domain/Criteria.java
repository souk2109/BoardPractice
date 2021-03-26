package org.example.domain;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.Data;
import lombok.extern.log4j.Log4j;

@Log4j
@Data
public class Criteria {
	private int pageNum;
	private int amount;
	
	private String type;
	private String keyword;
	
	public Criteria() {
		this(1,10);
	}

	public Criteria(int pageNum, int amount) {
		this.pageNum = pageNum;
		this.amount = amount;
	}
 
	
	public String[] getTypeArr() {
		return type == null? new String[]{}: type.split("");
	}
	
	// 간단하게 uri 생성
	public String getListLink() {
		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromPath("")
				.queryParam("pageNum", this.getPageNum())
				.queryParam("amount", this.getAmount())
				.queryParam("type", this.getType())
				.queryParam("keyword", this.getKeyword());

		return uriComponentsBuilder.toUriString();
	}
}
