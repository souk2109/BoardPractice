package org.example.controller;

import org.example.domain.BoardVO;
import org.example.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/ajax/*")
@RestController
public class AutoCompleteController {
	@Autowired
	private BoardService service;

	@GetMapping(value = "/board/{bno}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<BoardVO> getBoard(@PathVariable("bno") int bno) {
		BoardVO boardVO = service.get(bno);
		return new ResponseEntity<BoardVO>(boardVO, HttpStatus.OK);
	}
}
