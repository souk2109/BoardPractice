<%@page
	import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@page import="org.springframework.security.core.Authentication"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="/board002/user/doLogin" method="post">
		아이디 : <input name="id" type="text"> <br> 
		비밀번호 : <input name="pwd" type="password"> 
		<input type="submit" value="로그인">
	</form>
	<br>
	<div>
		<a href="/board002/user/register">회원가입</a>
	</div>
	<sec:authorize access="isAuthenticated()">
		<sec:authentication property="principal" var="user" />
	${user.username }
	${user.password }
	${user.nickname }

	</sec:authorize>
</body>
</html>