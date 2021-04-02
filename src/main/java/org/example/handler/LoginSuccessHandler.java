package org.example.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import lombok.extern.log4j.Log4j;

@Log4j
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
	 
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		List<String> authList = new ArrayList<String>();
		authentication.getAuthorities().forEach(auth -> {
			log.info("권한 :" + auth.getAuthority());
			authList.add(auth.getAuthority());
		});
		
		response.sendRedirect("/board002/board/list");
	}

}
