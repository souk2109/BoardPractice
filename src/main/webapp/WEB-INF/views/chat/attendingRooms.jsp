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
	<div class="col-xs-4 col-sm-4 text-center" style="height: 60px;">채팅방</div>
	<div class="col-xs-8 col-sm-8 text-center" style="height: 60px;">내용</div>
	
	<div class="col-xs-4 col-sm-4" style="height: 500px; border: 2px solid green;" id="chat-title">
	</div>
	<div class="col-xs-8 col-sm-8" style="height: 500px; border: 2px solid green;">
		<div style="height: 90%; overflow: auto;" id="chat-content">
			<div class="text-center">채널을 선택하세요!</div>
		</div>
		<div class="col-lg-12" style="height: 10%" hidden="true" id="chat-service">
			<!-- <span class="col-xs-4 col-sm-4">${user.nickname}</span> --> 
			<input class="col-xs-8 col-sm-8" type="text" id="msg" autocomplete="false">
			<button id="sendbtn" class="btn btn-primary col-xs-2 col-sm-2">전송</button>
			<button id="outbtn" class="btn btn-warning col-xs-2 col-sm-2">나가기</button>
		</div>
	</div>
</div>
<script type="text/javascript" src="/board002/resources/js/chat.js"></script>
<script type="text/javascript">
	const id = '<c:out value='${user.username}'/>'; // 사용자의 id
	const sender = '<c:out value='${user.nickname }'/>'; // 사용자의 닉네임
	let currentChnum = null; // 현재 보고있는 채팅의 채널 번호
	
	function getChatroomTitles() {
		chatService.getAllChatRooms(id, function(list) {
			$("#chat-title").html('');
			let str = '';
			for(var i=0; i<list.length; i++){
				if(list[i].validate == 2 || list[i].validate == 4){
					str += "<div class='text-center alert alert-warning chat-title' style='cursor:pointer' data-chnum=" + list[i].chnum + ">" + list[i].roomNick +"( "+list[i].currentNum +"/" + list[i].maxNum + ")</div>";
				}
			}
			$("#chat-title").append(str);
			 clickTitle();
		});
	}
	getChatroomTitles();
	function clickTitle() {
		// 특정 채팅방 제목을 클릭했을 때 채널 번호를 바꾼 후 해당 데이터를 가져옴
		$(".chat-title").on("click", function() {
			$("#chat-service").show(); // 채팅방 제목을 클릭했을 때 메세지를 작성하고 전송하는 view를 보여줌
			$(".chat-title").attr('class', 'alert alert-warning chat-title');
			$(this).attr('class', 'alert alert-success chat-title');
			
			currentChnum = $(this).data("chnum");
			userObj = {id:id, chnum:currentChnum};
			// id와 chnum을 넘겨서 받아와야한다.(id는 채팅방에 접속한 날짜를 얻어내기 위해서 사용)
			chatService.getChatMessage(userObj, function(list) {
				$("#chat-content").html('');
				let str = '';
				for(var i=0; i<list.length; i++){
					if(list[i].id == id){
						str += "<div class='alert alert-info' style='text-align:right'>나 : " + list[i].message + "</div>";
					}else{
						str += "<div class='alert alert-warning' style='text-align:left'>" + list[i].sender +" : "+ list[i].message + "</div>";
					}
				}
				$("#chat-content").append(str);
				scrolldown();
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
				let rawMessage = event.data;
				let messageArr = rawMessage.split("|");
				
				let chnum = null;
				let messageSender = null;
				let message = null;
				let messageId = null;
				
				if (messageArr.length == 4) {
					chnum = messageArr[0];
					messageSender = messageArr[1];
					message = messageArr[2];
					messageId = messageArr[3];
					
					console.log(chnum + "채널, 아이디: "+messageId + messageSender + " : [" + message + "]");
					console.log("보낸사람의 채널: "+chnum + ", 현재 보고있는 채널: "+currentChnum);
					if(parseInt(chnum) === currentChnum){
						if(id === messageId){
							$("#chat-content").append("<div class='alert alert-info' style='text-align:right'>나 :" + message + "</div>");
						}else{
							$("#chat-content").append("<div class='alert alert-warning'>"+ messageSender + ": " + message + "</div>");
						}
						scrolldown();
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
		console.log("보낼 채널은: "+ currentChnum);
		send(JSON.stringify({message: inputValue, sender:sender, id : id, chnum : currentChnum, action : 'SEND'}));			
		$("input#msg").val('');
	});
	
	
	// 채팅방을 나갈 시 db에서 삭제한다.(글은 유지하며 참여 인원에서 제외)
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
	};
	
	// 채팅창의 화면에서 최신 대화인 맨 아래쪽 글을 보여주기 위해 스크롤을 화면 맨 아래로 내려준다.
	function scrolldown() {
		$('#chat-content').scrollTop($('#chat-content')[0].scrollHeight);
	};
	connect();
</script>

<%@include file="../includes/footer.jsp"%>