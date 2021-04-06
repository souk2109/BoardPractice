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
				str += "<tr>";
				str += "<td>" + (i+1) + "</td>";
				str += "<td class='maxChNum'>" + list[i].chnum + "</td>";
				str += "<td class='hostNick'>" + list[i].hostNick + "</td>";
				str += "<td class='roomNick'>" + list[i].roomNick + "</td>";
				str += "<td class='maxNum'>" + list[i].maxNum + "</td>";
				// str += "<td><input class='form-control' readonly></input></td>"
				str += "<td>" + chatService.displayLongTime(list[i].regDate) + "</td>";
				str += "<td><button data-chnum=" + list[i].chnum + " class='btn btn-info modBtn'>수정</button></td>";
				str += "<td><button data-chnum="+ list[i].chnum + " class='btn btn-info delBtn'>삭제</button></td>";
				str += "</tr>";
			}
			$("#myRooms").append(str);
			btnService();
		});
	}
	 
	
	// 수정 또는 삭제시 
	function btnService() {
		// 삭제 버튼 클릭시
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
		
		// 수정 버튼 클릭시
		$(".modBtn").on("click", function() {
			// 기존에 있던 값을 저장
			let originHostNick = $(this).closest('tr').find('.hostNick').text();
			let originRoomNick = $(this).closest('tr').find('.roomNick').text();
			
			if($(this).html() == '수정'){
				$(this).html('완료');
				$(this).attr('class', 'btn btn-success');
				$(this).closest('tr').find('.maxNum').html(
						"<select class='form-control' name='maxNum'>"+
							"<option>1</option><option>2</option>" +
							"<option>3</option><option>4</option>" +
							"<option>5</option><option>6</option>" +
							"<option>7</option><option>8</option>" +
							"<option>9</option><option>10</option>" +
						"</select>");
				$(this).closest('tr').find('.roomNick').html("<input type='text' value='"+ originRoomNick +"'>");
			}else{
				let chnum = $(this).data("chnum");
				$(this).html('수정');
				$(this).attr('class', 'btn btn-info');
				// 수정된 제한 값을 변수에 담음
				let modMaxNum = $(this).closest('tr').find('select option:selected').text();
				let modRoomNick = $(this).closest('tr').children('.roomNick').find("input").val();
				console.log(modRoomNick);
				$(this).closest('tr').find('.roomNick').html("<div>"+ modRoomNick +"</div>");
				// $(this).closest('tr').find('.roomNick').html("<div>" + originRoomNick +"</div>");
				//let modRoomNick = $(this).closest('tr').find('.roomNick').html("");
				
				
				
				//let chatRoomObj = {chnum:chnum, maxNum:modMaxNum, };
				$(this).closest('tr').find('.maxNum').html(modMaxNum);
				// 변경할 코드 작성
				//chatService.updateChatRoom();
			}
			
	
			/* 
			let chnum = $(this).data("chnum");
			chatService.updateChatRoom(chnum, function(result) {
				console.log("삭제 여부 : "+result);
				$("#myRooms").html("");
				showList();
			});
			return;
			 */
		});
	}
	showList();
</script>
<%@include file="../includes/footer.jsp"%>