<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="../includes/header.jsp"%>
<sec:authentication property="principal" var="user"/>
<body>
<h1>채팅방 만들기</h1>

	${user.username }
	<form action="">
		<input>
	</form>
</body>
</html>


<%@include file="../includes/footer.jsp"%>