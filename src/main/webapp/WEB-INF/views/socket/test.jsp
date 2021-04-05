<!--  메세지 창에 id 대신 닉네임으로 변경 해야함 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="../includes/header.jsp"%>

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
					<span>${user.username }</span>
					<input type="text" id="msg" autofocus="autofocus" autocomplete="false">
					<button id="sendbtn" class="btn btn-primary">전송</button>
					<button id="outbtn" class="btn btn-warning">나가기</button>
				</div>
			</div>
		</div>
	</div>
	
	<script type="text/javascript">
		const userNick = '<c:out value="${user.nickname }"/>';
	 	const userId = '<c:out value="${user.username}"/>';
		function connect() {
			var was = new WebSocket("ws://localhost:8080/board002/socket/chatSocket");
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
						console.log(messageSender + " : [" + originMessage
								+ "]");
						
						if(userId == messageSender){
							$("#msgBox").append("<div class='alert alert-info' style='text-align:right'>나 :" + originMessage + "</div>");
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
					/* 
					setTimeout(function() {
						connect();
					}, 1000); 
					*/
				}
				socket.onerror = function(err) {
					console.log("Error : connection error");
				}
			}
		};
		
		// 전송버튼 클릭 시
		$("#sendbtn").on("click", function(e) {
			let inputValue = $("input#msg").val();
			send(JSON.stringify({message: inputValue, sender:userId, reciever : 'jon', chnum : 5, action : 'SEND'}));			
		});
		
		
		$("#outbtn").on("click", function(e) {
			let result = confirm('정말 방을 나가시겠습니까?');
			if(result){
				send(JSON.stringify({message: userId + '님이 퇴장하셨습니다.', sender:userId, reciever : 'jon', chnum : 5, action : 'OUT'}));	
				window.location.href = '/board002/board/list';
			}else{
				return;
			}
			 
		});
		
		function send(message) {
			if (!socket) {
				return;
			}
			socket.send(message);
		}
	</script>
	
	<script type="text/javascript">
			$("document").ready(function() {
				var socket = null;
				connect();
				window.onbeforeunload = function() {//브라우저 종료 및 닫기 감지
					socket.close();
				};
			});
		</script>

<%@include file="../includes/footer.jsp"%>