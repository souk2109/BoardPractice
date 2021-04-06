<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="../includes/header.jsp"%>
<sec:authentication property="principal" var="user"/>
	<h1>내가 만든 채팅방 보기</h1>
	<a href="/board002/chat/makeChat"><button class="btn btn-warning" style="margin-bottom: 30px; margin-top: 30px">방 생성하기</button></a>
	<!-- 반응형 테이블 생성  -->
	<div class="table-responsive">
		<table class="table table-bordered table-hover"  style="text-align: center;">
			<thead>
				<tr>
					<th>번호</th><th>방번호</th><th>닉네임</th><th>방이름</th><th>제한인원</th><th>생성일</th><th>수정</th><th>삭제</th>
				</tr>
			</thead>
			<tbody id="myRooms">
			</tbody>
		</table>
	</div>
 
<script type="text/javascript" src="/board002/resources/js/chat.js"></script>

<script type="text/javascript">
	const id = '<c:out value='${user.username}'/>';
	// ajax 통신으로 생성한 방의 목록을 받아옴
	function showList() {
		chatService.getMyChatRooms(id, function(list) {
			let str = '';
			console.log(list.length);
			for(var i=0; i<list.length; i++){
				str += '<tr>';
				str += '<td>' + (i+1) + '</td>';
				str += '<td>' + list[i].chnum + '</td>';
				str += '<td>' + list[i].hostNick + '</td>';
				str += '<td>' + list[i].roomNick + '</td>';
				str += '<td>' + list[i].maxNum + '</td>';
				str += '<td>' + chatService.displayLongTime(list[i].regDate) + '</td>';
				str += "<td><button data-chnum=" + list[i].chnum + " class='btn btn-priary modBtn'>수정</button></td>";
				str += "<td><button data-chnum="+ list[i].chnum + " class='btn btn-priary delBtn'>삭제</button></td>";
				str += '</tr>';
			}
			$("#myRooms").append(str);
			btnService();
		});
	}
	 
	
	// 수정 또는 삭제시 
	function btnService() {
		// 수정 버튼 클릭시
		$(".modBtn").on("click", function() {
			let a = $(this).data("chnum");
			alert(a + '클릭');
		});
		$(".delBtn").on("click", function() {
			let chnum = $(this).data("chnum");
			let delCheck = confirm("정말 삭제하시겠습니까?");
			if(delCheck){
				chatService.deleteChatRoom(chnum, function(result) {
					console.log("삭제 여부 : "+result);
					$("#myRooms").html("");
					showList();
				});
			}
			return;
		});
	}
	showList();
	 
</script>
<%@include file="../includes/footer.jsp"%>