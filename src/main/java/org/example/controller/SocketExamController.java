package org.example.controller;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/socket")
public class SocketExamController {
	@GetMapping("/test")
	public void test() {

	}
}
