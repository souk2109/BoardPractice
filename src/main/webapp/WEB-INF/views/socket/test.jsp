<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="../includes/header.jsp"%>
<sec:authorize access="isAnonymous()">
	<%
			response.sendRedirect("/board002/user/login");
		%>
</sec:authorize>

<sec:authorize access="isAuthenticated()">
<sec:authentication property="principal" var="user"/>
	<div class="row">
		<div class="col-lg-12">
			<h1 class="page-header">
				채팅하기
			</h1>
		</div>
	</div>
	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-default">
				<div class="panel-body">
					<div id="msgBox"></div>
				</div>
				<div class="panel-footer">
					<span id="uesrId">${user.username }</span>
					<input type="text" id="msg" autofocus="autofocus" autocomplete="false">
					<button id="btn" class="btn btn-primary">전송</button>
				</div>
			</div>
		</div>
	</div>
	
	<script type="text/javascript">
		let inputValue = $("input#msg").val();
		let socket = null;
	 	var userId = '<c:out value="${user.username}"/>';
	 	console.log("사용자 아이디 ; "+userId);
		function connect() {
			var was = new WebSocket(
					"ws://localhost:8080/board002/socket/chatSocket");
			socket = was;
			socket.onopen = function() {
				console.log('info: connection opened!');
				socket.onmessage = function(event) {
					var rawMessage = event.data;
					var messageArr = rawMessage.split("|");
					var messageSender = null;
					var originMessage = null;
					if (messageArr.length == 2) {
						messageSender = messageArr[0];
						originMessage = messageArr[1];
						console.log(messageSender + "가 [" + originMessage + "] 보냄");
						console.log("유저 아이디"+userId);
						if(userId == messageSender){
							$("#msgBox").append("<div class='alert alert-info'>나 :" + originMessage + "</div>");
						}else{
							$("#msgBox").append("<div class='alert alert-warning'>"+ messageSender + ": " + originMessage + "</div>");
						}
						 
					} else {
						socket.close();
						console.log("커넥션 닫음");
					}
				}

				socket.onclose = function(event) {
					console.log("Info : connection closed");
					setTimeout(function() {
						connect();
					}, 1000);
				}
				socket.onerror = function(err) {
					console.log("Error : connection error");
				}
			}
		};

		$("#btn").on("click", function(e) {
			if (!socket) {
				return;
			}
			let msg = $("input#msg");
			 
			// 소캣 메세지 전송
			socket.send(msg.val());
			msg.val("");

		});
	</script>
	<script type="text/javascript">
			$("document").ready(function() {
				connect();
			});
		</script>
</sec:authorize>

<%@include file="../includes/footer.jsp"%>