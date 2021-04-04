package org.example.controller;

import org.example.security.SessionUser; 
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/socket")
public class SocketExamController {
	@GetMapping("/test")
	public void test(Model model) {
		SessionUser user = (SessionUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("authenticatedUser",user.getUsername());
	}
}
