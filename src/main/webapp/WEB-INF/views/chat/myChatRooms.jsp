<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="../includes/header.jsp"%>
<sec:authentication property="principal" var="user"/>
	<h1>내가 만든 채팅방 보기</h1>
	<ul id="myRooms">
	
	</ul>
<script type="text/javascript" src="/board002/resources/js/chat.js"></script>

<script type="text/javascript">
	const id = '<c:out value='${user.username}'/>';
	let myRoomList = null;
	
	// ajax 통신으로 생성한 방의 목록을 받아옴
	chatService.getMyChatRooms(id, function(list) {
		let str = '';
		console.log(list.length);
		for(var i=0; i<list.length; i++){
			str += '<li>';
			str += '<span>' + list[i].chnum + '</span>';
			str += '<span>' + list[i].hostNick + '</span>';
			str += '<span>' + list[i].roomNick + '</span>';
			str += '<span>' + list[i].maxNum + '</span>';
			str += '<span>' + list[i].regDate + '</span>';
			str += '</li>';
		}
		$("#myRooms").html(str);
		console.log(str);
	}, function() {
		
	})
</script>
<%@include file="../includes/footer.jsp"%>