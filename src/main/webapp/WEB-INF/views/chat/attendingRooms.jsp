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
		<div style="height: 90%; overflow: auto;" id="chat-content">
		</div>
		<div style="height: 10%">
			<span>${user.nickname}</span> <input type="text" id="msg" autofocus="autofocus" autocomplete="false">
			<button id="sendbtn" class="btn btn-primary">전송</button>
			<button id="outbtn" class="btn btn-warning">나가기</button>
		</div>
	</div>
</div>
<script type="text/javascript" src="/board002/resources/js/chat.js"></script>
<script type="text/javascript">
	const id = '<c:out value='${user.username}'/>'; // 사용자의 id
	const sender = '<c:out value='${user.nickname }'/>'; // 사용자의 닉네임
	let chnum = null; // 채널 번호
	
	function getChatroomTitles() {
		chatService.getAllChatRooms(id, function(list) {
			$("#chat-title").html('');
			let str = '';
			for(var i=0; i<list.length; i++){
				if(list[i].validate == 2 || list[i].validate == 4){
					str += "<div class='alert alert-warning chat-title' style='cursor:pointer' data-chnum=" + list[i].chnum + ">" + list[i].roomNick +"( "+list[i].currentNum +"/" + list[i].maxNum + ")</div>";
				}
			}
			$("#chat-title").append(str);
			 clickTitle();
		});
	}
	getChatroomTitles();
	function clickTitle() {
		// 특정 채팅방 제목을 클릭했을 때 채널 번호를 바꿔서 담아주고 채팅 데이터를 가져옴
		$(".chat-title").on("click", function() {
			chnum = $(this).data("chnum");
			// 그에 해당하는 대화목록을 받아옴
			chatService.getChatMessageByChnum(chnum, function(list) {
				$("#chat-content").html('');
				let str = '';
				for(var i=0; i<list.length; i++){
					if(list[i].id == id){
						str += "<div class='alert alert-info' style='text-align:right'>나 : " + list[i].message + "</div>";
					}else{
						str += "<div class='alert alert-info' style='text-align:left'>" + list[i].sender +" : "+ list[i].message + "</div>";
					}
				}
				$("#chat-content").append(str);
			});
		});	
	}
</script>

<!-- 웹소켓 통신관련 코드 -->
<script>
	function connect() {
		socket.onopen = function() {
			console.log('info: connection opened!');
			socket.onmessage = function(event) {
				var rawMessage = event.data;
				var messageArr = rawMessage.split("|");
				
				var chnum = null;
				var messageSender = null;
				var message = null;
				var messageId = null;
				
				if (messageArr.length == 4) {
					chnum = messageArr[0];
					messageSender = messageArr[1];
					message = messageArr[2];
					messageId = messageArr[3];
					console.log(chnum + "채널, 아이디: "+messageId + messageSender + " : [" + message + "]");
					
					if(id === messageId){
						$("#chat-content").append("<div class='alert alert-info' style='text-align:right'>나 :" + message + "</div>");
					}else{
						$("#chat-content").append("<div class='alert alert-warning'>"+ messageSender + ": " + message + "</div>");
					}
					 
				} else {
					console.log("커넥션 닫음");
					//socket.close();
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
	
	// 전송버튼 클릭 시
	$("#sendbtn").on("click", function(e) {
		let inputValue = $("input#msg").val();
		send(JSON.stringify({message: inputValue, sender:sender, id : id, chnum : chnum, action : 'SEND'}));			
	});
	
	
	$("#outbtn").on("click", function(e) {
		let result = confirm('정말 방을 나가시겠습니까?');
		if(result){
			send(JSON.stringify({message: userId + '님이 퇴장하셨습니다.', sender:sender, id : id, chnum : chnum, action : 'OUT'}));	
			window.location.href = '/board002/board/list';
		}else{
			return;
		}
		 
	});
	
	function send(message) {
		if (!socket) {
			console.log("error! 소켓이 없음!");
			return;
		}
		socket.send(message);
	}
	 
	connect();
</script>

<%@include file="../includes/footer.jsp"%>