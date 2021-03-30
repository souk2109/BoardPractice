package org.example.controller;

import java.util.List;

import org.example.domain.Criteria;
import org.example.domain.ReplyPageDTO;
import org.example.domain.ReplyVO;
import org.example.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j;

@RequestMapping("/reply/*")
@RestController
@Log4j
public class ReplyController {
	@Autowired
	private ReplyService service;

	// consumes = "application/json" : application/json의 형태만 받음
	@PostMapping(value = "/new", consumes = "application/json", produces = { MediaType.TEXT_PLAIN_VALUE })
	public ResponseEntity<String> create(@RequestBody ReplyVO replyVO) {
		int result = service.register(replyVO);
		log.info("댓글 등록 !");
		return result == 1 ? new ResponseEntity<String>("success", HttpStatus.OK)
				: new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@GetMapping(value = "/pages/{bno}/{page}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<ReplyPageDTO> getList(@PathVariable("bno") int bno, @PathVariable("page") int page) {
		Criteria criteria = new Criteria(page, 10);
		List<ReplyVO> list = service.getList(bno, criteria);
		log.info("게시글" + bno + "번의 댓글들 조회!");
		list.forEach(reply -> log.info(reply));
		return new ResponseEntity<ReplyPageDTO>(service.getListPage(criteria, bno), HttpStatus.OK);
	}

	@DeleteMapping(value = "/{rno}", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> remove(@PathVariable("rno") int rno) {
		int result = service.remove(rno);
		log.info(rno + "번의 댓글 삭제!");
		return result == 1 ? new ResponseEntity<String>("success", HttpStatus.OK)
				: new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@GetMapping(value = "/get/{rno}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<ReplyVO> get(@PathVariable("rno") int rno) {
		ReplyVO replyVO = service.get(rno);
		log.info(rno + "번의 댓글 조회!");
		return new ResponseEntity<ReplyVO>(replyVO, HttpStatus.OK);
	}

	@RequestMapping(method = { RequestMethod.PUT,
			RequestMethod.PATCH }, value = "/{rno}", consumes = "application/json", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> modify(@RequestBody ReplyVO replyVO, @PathVariable("rno") int rno) {
		replyVO.setRno(rno);
		int result = service.modify(replyVO);
		log.info(rno + "번의 댓글 수정!");
		return result == 1 ? new ResponseEntity<String>("success", HttpStatus.OK)
				: new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
