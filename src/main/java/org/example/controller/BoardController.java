package org.example.controller;

import java.util.stream.IntStream;

import org.example.domain.BoardVO;
import org.example.domain.Criteria;
import org.example.domain.PageDTO;
import org.example.service.BoardService;
import org.example.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/board/*")
@Log4j
public class BoardController {
	@Setter(onMethod_ = @Autowired)
	private BoardService service;

	@GetMapping("/list")
	public void list(Model model, Criteria criteria) {
		int total = service.getCount(criteria);
		log.info("토탈은 : " + total);
		PageDTO pageDTO = new PageDTO(criteria, total);
		log.info("pageDTO : " + pageDTO);
		log.info(service.getList(criteria));
		model.addAttribute("list", service.getList(criteria));
		model.addAttribute("pageMaker", pageDTO);
		model.addAttribute("total", total);
	}

	@PostMapping("/register")
	public String register(BoardVO boardVO, RedirectAttributes rttr) {
		log.info("vo: " + boardVO);
		service.register(boardVO);
		log.info("register : " + boardVO);
		rttr.addFlashAttribute("result", boardVO.getBno());
		return "redirect:/board/list";
	}

	@GetMapping({ "/get", "/modify" })
	public void get(@RequestParam("bno") int bno, Model model, Criteria criteria) {
		BoardVO boardVO = service.get(bno);
		log.info("GET : -> " + boardVO);
		model.addAttribute("board", boardVO);
	}

	@PostMapping("/modify")
	public String modify(BoardVO boardVO, Criteria criteria, RedirectAttributes rttr) {
		if (service.modify(boardVO)) {
			rttr.addFlashAttribute("result", "success");
		}
		log.info("criteria uri: " + criteria.getListLink());
		return "redirect:/board/list" + criteria.getListLink();
	}

	@PostMapping("/remove")
	public String remove(@RequestParam("bno") int bno, Criteria criteria, RedirectAttributes rttr) {
		if (service.remove(bno))
			rttr.addFlashAttribute("result", "success");
		log.info("criteria uri: " + criteria.getListLink());
		return "redirect:/board/list" + criteria.getListLink();
	}

	@GetMapping("/register")
	public void register() {
	}
}
