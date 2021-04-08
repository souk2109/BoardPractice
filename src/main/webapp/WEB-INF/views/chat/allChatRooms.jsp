<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="../includes/header.jsp"%>
<sec:authentication property="principal" var="user"/>
	<h1>모든 채팅방 보기</h1>
	<a href="/board002/chat/makeChat">
		<button class="btn btn-warning" style="margin-bottom: 30px; margin-top: 30px">방 생성하기
		</button>
	</a>
	<!-- 반응형 테이블 생성  -->
	<div class="table-responsive">
		<table class="table table-bordered table-hover"  style="text-align: center;">
			<thead>
				<tr>
					<th>번호</th><th>방장</th><th>방이름</th><th>제한인원</th><th>생성일</th><th>참여하기</th>
				</tr>
			</thead>
			<tbody id="myRooms">
			</tbody>
		</table>
	</div>
 
<script type="text/javascript" src="/board002/resources/js/chat.js"></script>

<script type="text/javascript">
	const id = '<c:out value='${user.username}'/>';
	 
	function showChatRooms() {
		// ajax 통신으로 생성한 방의 목록을 받아옴
		// 사용자 별로 특정 채팅방에 참여 신청을 한 경우 '처리중'으로 보이게 변경 [2021/04/08]
		
		// host id도 같이 받아서 신청할 때 db에 넣어주기
		chatService.getAllChatRooms(id, function(list) {
			console.log(list);
			$("#myRooms").html('');
			let str = '';
			for(var i=0; i<list.length; i++){
				str += "<tr>";
				str += "<td>" + (i+1) + "</td>";
				str += "<td>" + list[i].hostNick + "</td>";
				str += "<td class='roomNick'>" + list[i].roomNick + "</td>";
				str += "<td class='maxNum'>" + list[i].maxNum + "</td>";
				str += "<td>" + chatService.displayShortTime(list[i].regdate) + "</td>";
				if(list[i].validate == 1){
					str += "<td><button class='btn btn-priary request' disabled>처리중</button>";
					str += "<button class='btn btn-priary warning'>취소</button></td>";
				}else if(list[i].validate == 0){
					str += "<td><button class='btn btn-success request'>신청</button></td>";
				}else if(list[i].validate == 2){
					str += "<td><button class='btn request' disabled>내방</button></td>";
				}
				else{
					str += "<td><button class='btn btn-priary request'>뭐할까</button></td>";
				}
				str += "<input type='hidden' class='chatNum' value='" + list[i].chnum + "'></input>";
				str += "</tr>";
			}
			$("#myRooms").append(str);
			btnService();
		});
	};
	function btnService() {
		$(".request").on("click", function() {
			let chnum = $(this).closest("tr").find(".chatNum").val();
			console.log(chnum);
			let requestInfo = {id:id, chnum:chnum};		
			let requestCheck = confirm("정말 신청하시겠습니까?");
			if(requestCheck){
				chatService.requestJoinRoom(requestInfo, function(result) {
					showChatRooms();
//					alert(result + "신청이 완료되었습니다.");
				}, function() {
					alert("참여 중 이거나 이미 신청한 채팅방입니다.");
				});
			}
		});
	};
	showChatRooms();
	
</script>
<%@include file="../includes/footer.jsp"%>