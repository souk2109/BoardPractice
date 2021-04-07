<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="../includes/header.jsp"%>
<sec:authentication property="principal" var="user"/>
	<h1>모든 채팅방 보기</h1>
	<a href="/board002/chat/makeChat"><button class="btn btn-warning" style="margin-bottom: 30px; margin-top: 30px">방 생성하기</button></a>
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
	// ajax 통신으로 생성한 방의 목록을 받아옴
	chatService.getAllChatRooms(function(list) {
		let str = '';
		console.log(list.length);
		for(var i=0; i<list.length; i++){
			str += '<tr>';
			str += '<td>' + (i+1) + '</td>';
			str += '<td>' + list[i].hostNick + '</td>';
			str += '<td>' + list[i].roomNick + '</td>';
			str += '<td>' + list[i].maxNum + '</td>';
			str += '<td>' + chatService.displayShortTime(list[i].regDate) + '</td>';
			str += "<td><button class='btn btn-priary'>신청</button></td>";
			str += '</tr>';
		}
		$("#myRooms").append(str);
	}, function() {
		
	})
</script>
<%@include file="../includes/footer.jsp"%>