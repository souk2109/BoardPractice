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
	
	<div class="col-xs-1 col-sm-1" style="height: 500px;"></div>
	<div class="col-xs-2 col-sm-2" style="height: 500px; border: 2px solid green;" id="chat-title">
	</div>
	<div class="col-xs-5 col-sm-6" style="height: 500px; border: 2px solid green;">
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
	<div class="col-xs-3 col-sm-2" style="height: 500px;">
		<div class="text-center" style="background-color: yellow;">참여중인 사람
			<div id="current-people" style="background-color: #FFFFCC"></div>
		</div>
	</div>
	<div class="col-xs-1 col-sm-1" style="height: 500px;"></div>
</div>


<script type="text/javascript" src="/board002/resources/js/chat.js"></script>
<script type="text/javascript">
	const id = '<c:out value='${user.username}'/>'; // 사용자의 id
	const sender = '<c:out value='${user.nickname }'/>'; // 사용자의 닉네임
	let currentChnum = null; // 현재 보고있는 채팅의 채널 번호
	let currentHostId = null; // 현재 보고있는 채팅방의 방장id
	function getChatroomTitles() {
		chatService.getAllChatRooms(id, function(list) {
			console.log(list);
			$("#chat-title").html('');
			let str = '';
			for(var i=0; i<list.length; i++){
				// 방장이거나 참여중인 사람이거나
				if(list[i].validate == 2 || list[i].validate == 4){
					// 읽지 않은 채팅 수 가져옴
					let unReadChatCount = null;
					chatService.getUnReadChatCount({chnum : list[i].chnum, id : id} , function(result) {
						unReadChatCount = result;
						console.log('unReadChatCount : ' + unReadChatCount);
					});
					str += "<div class='text-center alert alert-warning chat-title' style='cursor:pointer' data-hostid="+list[i].hostId+" data-chnum=" + list[i].chnum + "><span data-unreadNum=" + list[i].chnum + " style='font-weight:900; color: blue;'>" + unReadChatCount+ "</span>&nbsp&nbsp" + list[i].roomNick + "</div>";
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
			$(".chat-title").attr('class', 'text-center alert alert-warning chat-title');
			$(this).attr('class', 'text-center alert alert-success chat-title');
			console.log('currentChnum : '+ currentChnum);
			
			// 기존에 있던 체널을 나오고 다른 채널의 채팅방을 보고 있는 것이므로, 기존의 방에서 out 해준다.
			if(currentChnum !== null){
				chatService.updateOutParticipate(userObj, function(result) {
					if(result === 'success'){
						send(JSON.stringify({sender:sender, id : id, chnum : currentChnum, action : 'UNSEE'}));
						console.log(sender + ", " + id + ", "+ currentChnum);
						console.log("[updateOutParticipate] "+result);	
					}else{
						console.log("[updateOutParticipate] "+result);
						return;
					}
				});
			}
			currentChnum = $(this).data("chnum");
			currentHostId = $(this).data("hostid");
			send(JSON.stringify({sender:sender, id : id, chnum : currentChnum, action : 'SEE'}));
			
			$("[data-unreadNum=" + currentChnum + "]").html('0');	

			userObj = {id:id, chnum:currentChnum};

			chatService.updateInParticipate(userObj, function(result) {
				console.log("[updateInParticipate] "+result);	
				if(result === 'fail') {return;}
			});
			
			// id와 chnum을 넘겨서 받아와야한다.(id는 채팅방에 접속한 날짜를 얻어내기 위해서 사용)
			chatService.getChatMessage(userObj, function(list) {
				console.log(list);
				$("#chat-content").html('');
				let str = '';
				for(var i=0; i<list.length; i++){
					if(list[i].action == 'SEND'){
						if(list[i].id == id){
							str += "<div class='alert alert-info' style='text-align:right'>나 : " + list[i].message + "<div style='color:#8bafc1'>" + chatService.messageTime(list[i].sendDate) + "</div></div>";
						}else{
							str += "<div class='alert alert-warning' style='text-align:left'>" + list[i].sender +" : "+ list[i].message + "<div style='color:#b9b189'>" + chatService.messageTime(list[i].sendDate) + "</div></div>";
						}	
					}else if(list[i].action == 'OUT'){
						str += "<div class='alert' style='text-align:center'>" + list[i].message +"<span style='color:#b9b189'>" + chatService.messageTime(list[i].sendDate) + "</span></div>";
					}else if(list[i].action == 'JOIN'){
						str += "<div class='alert' style='text-align:center'>" + list[i].message +"<span style='color:#b9b189'>" + chatService.messageTime(list[i].sendDate) + "</span></div>";
					}
				}
				$("#chat-content").append(str);
				scrolldown();
			});
			// db로 부터 받아와서 현재 채팅방을 보고있는 사람에 대해 표시함
			chatService.getChatParticipateList(currentChnum, function(list) {
				$("#current-people").html('');
				let str='';
				for(var i=0; i< list.length; i++){
					if(list[i].enable === true){
						str += "<div data-nickname="+list[i].nickname+">" + list[i].nickname + "&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<span style='background-color: green' class='badge'>On</span></div>";
					}else{
						str += "<div data-nickname="+list[i].nickname+">" + list[i].nickname + "&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<span style='background-color: red' class='badge'>Off</span></div>";
					}
				}
				$("#current-people").append(str);
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
				
				let action = messageArr[0];
				let chnum = null;
				let messageSender = null;
				let message = null;
				let messageId = null;
				 
				if(action === 'SEND'){
					if (messageArr.length === 5) {
						chnum = messageArr[1];
						messageSender = messageArr[2];
						message = messageArr[3];
						messageId = messageArr[4];
						sendDate = new Date();
						
						let unreadMessageNum = $("[data-unreadNum=" + chnum + "]").html();
						
						console.log("currentChnum : "+currentChnum);
						// 안읽은 채팅 수 증가해줌
						if(parseInt(chnum) !== currentChnum){
							$("[data-unreadNum=" + chnum + "]").html(parseInt(unreadMessageNum)+1);	
						}
						
						
						console.log("messageArr.length : "+ messageArr.length + ", " + chnum + "채널, 아이디: "+messageId + messageSender + " : [" + message + "] action : "+ action);
						// 보낸 사람과 받는사람이 같은 채널인 경우
						if(parseInt(chnum) === currentChnum){
							if(id === messageId){
								$("#chat-content").append("<div class='alert alert-info' style='text-align:right'>나 :" + message + "<div style='color:#8bafc1'>"+ chatService.messageTime(sendDate) + "</div></div>");
							}else{
								$("#chat-content").append("<div class='alert alert-warning'>"+ messageSender + ": " + message +"<div style='color:#b9b189'>" + chatService.messageTime(sendDate) + "</div></div>");
							}
							scrolldown();
						}
					}else{
						alert('SEND 잘못된 전송!');
					}
				}else if(action === 'OUT'){
					if (messageArr.length === 5) {
						chnum = messageArr[1];
						messageSender = messageArr[2];
						message = messageArr[3];
						messageId = messageArr[4];
						sendDate = new Date();
						console.log(chnum + "채널, 아이디: "+messageId + messageSender + " : [" + message + "] action : "+ action);
						
						// 보낸 사람과 받는사람이 같은 채널인 경우
						if(parseInt(chnum) === currentChnum){
							$("[data-nickname=" + messageSender + "]").html('');
							$("#chat-content").append("<div class='alert' style='text-align:center'>" + message +"<span style='color:#b9b189'>" + chatService.messageTime(sendDate) + "</span></div>");
							scrolldown();
						}
					}else{
						alert('OUT 잘못된 전송!');
					}
				}else if(action === 'SEE'){
					if (messageArr.length === 4) {
						chnum = messageArr[1];
						messageSender = messageArr[2];
						messageId = messageArr[3];
						if(parseInt(chnum) === currentChnum){
							$("[data-nickname=" + messageSender + "]").html(messageSender + "&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<span style='background-color: green' class='badge'>On</span>");
						}
					}
				}else if(action === 'UNSEE'){
					if (messageArr.length === 4) {
						chnum = messageArr[1];
						messageSender = messageArr[2];
						messageId = messageArr[3];
						if(parseInt(chnum) === currentChnum){
							$("[data-nickname=" + messageSender + "]").html(messageSender + "&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<span style='background-color: red' class='badge'>Off</span>");
						}
					}
				}
			}				
			
			// 보고있을 떄 : SEE 초록불(참여중) , 안보고있을 떄 : UNSEE ,부재중 
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
		send(JSON.stringify({action : 'SEND', message: inputValue, sender:sender, id : id, chnum : currentChnum}));			
		$("input#msg").val('');
	});
	
	
	// 채팅방을 나갈 시 db에서 삭제한다.(글은 유지하며 참여 인원에서 제외) ok [2021/04/13] 
	$("#outbtn").on("click", function(e) {
		let result = confirm('정말 방을 나가시겠습니까?');
		let outResult = null;
		if(result){
			let outObj = {id:id, chnum: currentChnum};
			chatService.outRoomRequest(outObj, function(deleteResult) {
				outResult = deleteResult;
				// 정상적으로 validate를 변경했거나, 참여 인원수가 0명이라서 방이 삭제된 경우.
				if(deleteResult === "success" || deleteResult === "deleteRoom"){
					window.location.href = '/board002/chat/attendingRooms';
				}
			}, function() {
				alert("에러로 퇴장하지 못했습니다..");
			});
			// 정상적으로 validate를 변경했을 경우(인원수는 1명 이상이므로 방이 삭제되지 않았다.)
			if(outResult === "success"){
				// 방 주인이 나가는 경우
				if(currentHostId === id){
					chatService.unableChatRoom(currentChnum, function(result) {
						send(JSON.stringify({action : 'OUT', message: '방장님이 퇴장하셨습니다.', sender:sender, id : id, chnum : currentChnum}));
					}, function() {
						console.log("에러로 퇴장하지 못했습니다..");
					});
				}
				// 일반 사용자가 나가는 경우
				else{
					send(JSON.stringify({action : 'OUT', message: sender + '님이 퇴장하셨습니다.', sender:sender, id : id, chnum : currentChnum}));
				}	
			}
			
		}
		else{
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