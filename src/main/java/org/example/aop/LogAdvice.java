package org.example.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.example.service.BoardService;
import org.example.service.BoardServiceImpl;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j;

@Component
@Aspect
@Log4j
public class LogAdvice {
	@Before("execution(* org.example.service.*.*(..))")
	public void test() {
		log.info("□■□■□■□■□■□■□■□■□■□■□■□■□");
		log.info("vevvvvvvvvvv");
		log.info("□■□■□■□■□■□■□■□■□■□■□■□■□");
	}

	@Before("within(org.example.service.BoardService*)")
	public void test2() {
		log.info("nnnnnnnnnnnnn");
	}

	/*
	 * @AfterReturning(pointcut =
	 * "execution(* org.example.service.BoardService*.*(*))", returning = "result")
	 * public void aftert(Object result) { Object getObj = result; if(result ==
	 * null) getObj = ""; log.info("결과 : " + getObj); }
	 */

	/*
	 * @Around("execution(* org.example.service.*.*(*))") public void
	 * arount(ProceedingJoinPoint joinPoint) { Object result = null; Object[]
	 * className = joinPoint.getArgs(); log.info("클래스 명: "+className[0]); }
	 */
}
