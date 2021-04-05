<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="../includes/header.jsp"%>
<sec:authentication property="principal" var="user"/>

<h1>채팅방 만들기</h1>
	<form id="mkchatForm" action="/board002/chat/makeChat" method="post">
		아이디 <input type="text" name="id" readonly="readonly" value="${user.username }"><br>
		별명 <input type="text" name="hostNick"> <br>
		방 이름  <input type="text" name="roomNick" ><br>
		인원수<input type="text" name="maxNum"><br>
		<input id="mkchatSubmitBtn" type="submit" value="생성하기"><br>
	</form>
	
<script type="text/javascript">
	let inputForm = $("#inputForm");
	let mkchatSubmitBtn = $("#mkchatSubmitBtn");
	 
	// 인원수가 숫자인지 검사. 나중에 스크롤로 선택하는 것으로 변경하면 수정할 예정
	$(mkchatSubmitBtn).on("click", function(e) {
		 
		let hostNick = $("input[name='hostNick']").val().trim();
		let roomNick = $("input[name='roomNick']").val().trim();
		let maxNum = $("input[name='maxNum']").val().trim();
		
		if(hostNick == null || hostNick == ""){
			alert('별명을 입력하세요');
			e.preventDefault();
		}
		if(roomNick == null || roomNick == ""){
			alert('방 제목을 입력하세요');
			e.preventDefault();
		}
		if(isNaN(maxNum)|| maxNum == ""){
			alert('인원수에 숫자를 입력하세요');
			e.preventDefault();
		}
	});
</script>
<%@include file="../includes/footer.jsp"%>