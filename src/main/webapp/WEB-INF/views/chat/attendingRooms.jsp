<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../includes/header.jsp"%>
<sec:authentication property="principal" var="user" />

<div class="row">
	<div class="col-lg-12">
		<h1 class="page-header">채팅하기</h1>
	</div>
</div>

<div class="row">
	<div class="col-xs-4 col-sm-4" style="height: 60px">방 제목</div>
	<div class="col-xs-8 col-sm-8" style="height: 60px">채팅내용</div>
	
	<div class="col-xs-4 col-sm-4" style="height: 500px; border: 2px solid green;" id="chat-title">
	</div>
	<div class="col-xs-8 col-sm-8" style="height: 500px; border: 2px solid green;">
		<div style="height: 90%; overflow: auto;">
			<div class='alert alert-info' style='text-align:right'>나 블라블라</div>
			<div class='alert alert-warning'>너 블라블라</div>
			<div class='alert alert-info' style='text-align:right'>나 블라블라</div>
			<div class='alert alert-warning'>너 블라블라</div>
			<div class='alert alert-info' style='text-align:right'>나 블라블라</div>
			<div class='alert alert-warning'>너 블라블라</div>
		</div>
		<div style="height: 10%">
			<span>${user.username }</span> <input type="text" id="msg" autofocus="autofocus" autocomplete="false">
			<button id="sendbtn" class="btn btn-primary">전송</button>
			<button id="outbtn" class="btn btn-warning">나가기</button>
		</div>
	</div>
</div>
<script type="text/javascript" src="/board002/resources/js/chat.js"></script>
<script type="text/javascript">
	const id = '<c:out value='${user.username}'/>';
	function getChatroomTitles() {
		chatService.getAllChatRooms(id, function(list) {
			$("#chat-title").html('');
			let str = '';
			for(var i=0; i<list.length; i++){
				if(list[i].validate == 2 || list[i].validate == 4){
					str += "<div class='alert alert-warning'>" + list[i].roomNick +"( "+list[i].currentNum +"/" + list[i].maxNum + ")</div>";
				}
			}
			$("#chat-title").append(str);
		});
	}
	getChatroomTitles();
</script>

<%@include file="../includes/footer.jsp"%>