<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<sec:authentication property="principal" var="user"/>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
</body>
<script type="text/javascript">
	const id = '<c:out value='${user.username}'/>'; 
	let userId = '<c:out value="${userId }"/>'; // 이 방에 참여하고 있는 모든 회원들의 아이디
	let userIdList = userId.split("|");
	
	for(var i=0; i<userIdList.length; i++){
		if(userIdList[i] === id){
			break;
		}
	}
	
	alert(userIdList.length + "," + userIdList[0]);
</script>
</html>