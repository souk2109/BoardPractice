package org.example.domain;

import lombok.Data;

@Data
public class PageDTO {
	private int endPage;
	private int startPage;
	private boolean prev;
	private boolean next;
	private int total;
	private Criteria criteria;
	
	public PageDTO(Criteria criteria, int total){
		this.criteria = criteria;
		this.total = total;
		this.endPage = (int) Math.ceil(criteria.getPageNum()/10.0)*10;
		this.startPage = endPage-9;
		int realEnd = (int) Math.ceil((total*1.0)/criteria.getAmount());
		if(realEnd < endPage) {
			endPage = realEnd;
		}
		this.next = endPage < realEnd;
		this.prev = startPage > 1;
	}
}
