package org.example.controller;

import org.example.domain.UserVO;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/user/*")
@Log4j
public class UserController {
	
	@Autowired
	private UserService service;
	
	@GetMapping("/login")
	public void login() {
		
	}
	
	@GetMapping("/register")
	public void register() {
		
	}
	
	@PostMapping(value = "/doRegister")
	public String doRegister(UserVO userVO) {
		int result = service.registerUser(userVO);
		log.info("회원 : " + userVO);
		return "redirect:/user/login";
	}
}
