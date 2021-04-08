<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="../includes/header.jsp"%>
<sec:authentication property="principal" var="user"/>
	<div>
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
	</div>
	
	<div>
		<h1>채팅 요청 보기</h1>
		
		<div class="table-responsive">
			<table class="table table-bordered table-hover"  style="text-align: center;">
				<thead>
					<tr>
						<th>번호</th><th>신청인</th><th>방이름</th><th>신청일</th><th>승인</th><th>거절</th>
					</tr>
				</thead>
				<tbody id="myRequests">
				</tbody>
			</table>
		</div>
	</div>
<script type="text/javascript" src="/board002/resources/js/chat.js"></script>

<script type="text/javascript">
	const id = '<c:out value='${user.username}'/>';
	// ajax 통신으로 생성한 방의 목록을 받아옴
	function showMyRoomList() {
		chatService.getMyChatRooms(id, function(list) {
			let str = '';
			// 받은 목록을 화면에 띄움
			for(var i=0; i<list.length; i++){
				str += "<tr>";
				str += "<td>" + (i+1) + "</td>";
				str += "<td class='maxChNum'>" + list[i].chnum + "</td>";
				str += "<td class='hostNick'>" + list[i].hostNick + "</td>";
				str += "<td class='roomNick'>" + list[i].roomNick + "</td>";
				str += "<td class='maxNum'>" + list[i].maxNum + "명</td>";
				str += "<td>" + chatService.displayLongTime(list[i].regDate) + "</td>";
				str += "<td><button data-chnum=" + list[i].chnum + " class='btn btn-info modBtn'>수정</button></td>";
				str += "<td><button data-chnum="+ list[i].chnum + " class='btn btn-info delBtn'>삭제</button></td>";
				str += "</tr>";
			}
			$("#myRooms").append(str);
			btnService();
		});
	}
	// 내가 만든 채팅방에 요청 목록을 가져옴
	function showMyRequestList() {
		chatService.getMyRoomRequest(id, function(list) {
			console.log(list);
			let str = '';
			// 받은 목록을 화면에 띄움
			for(var i=0; i<list.length; i++){
				str += "<tr>";
				str += "<td>" + (i+1) + "</td>";
				str += "<td class='userid'>" + list[i].userid + "</td>";
				str += "<td class='roomNick'>list[i].roomNick</td>";
				str += "<td class='regdate'>list[i].regDate</td>";
				str += "<td><button class='btn btn-info'>승인하기</button></td>";
				str += "<td><button class='btn btn-info'>거절하기</button></td>";
				str += "</tr>";
			}
			$("#myRequests").append(str);
			btnService();
		});
	} 
	
	// 수정 또는 삭제 버튼 클릭시
	function btnService() {
		// 삭제 버튼 클릭시
		$(".delBtn").on("click", function() {
			let chnum = $(this).data("chnum");
			let delCheck = confirm("정말 삭제하시겠습니까?");
			if(delCheck){
				chatService.deleteChatRoom(chnum, function(result) {
					console.log("삭제 여부 : "+result);
					$("#myRooms").html("");
					showMyRoomList();
				});
			}
			return;
		});
		
		// 수정 버튼 클릭시
		$(".modBtn").on("click", function() {
			// 기존에 있던 값을 저장
			let originHostNick = $(this).closest('tr').find('.hostNick').text();
			let originRoomNick = $(this).closest('tr').find('.roomNick').text();
			let originMaxNum = $(this).closest('tr').find('.maxNum').text();
	
			if($(this).html() == '수정'){
				$(this).html('완료');
				$(this).attr('class', 'btn btn-success');
				
				// select를 jstl for문으로 수정 [2021/04/06]
				// jstl if문을 통해서 originMaxNum과 i를 비교해 기존에 있던 값에 selected 속성을 부여하려고 했지만 
				// 실행 순서가 javascript -> el 순이여서 불가능핟. 나중에 다른 방법이 있을까?
				$(this).closest('tr').find('.maxNum').html(
					"<select class='form-control' name='maxNum'>"+
						"<c:forEach begin='1' end='10' var='i'>"+
							"<option value='${i }'>${i}명</option>"+
						"</c:forEach>" +
					"</select>"
				);
				
				$(this).closest('tr').find('.roomNick').html("<input type='text' value='"+ originRoomNick +"'>");
			}else{
				let chnum = $(this).data("chnum");
				$(this).html('수정');
				$(this).attr('class', 'btn btn-info');
				
				// 수정된 제한 값을 변수에 담음
				let modMaxNum = $(this).closest('tr').find('select option:selected').val();
				let modRoomNick = $(this).closest('tr').children('.roomNick').find("input").val();
				
				
				// ajax 요청을 위한 json 객체 생성
				let chatRoomObj = {chnum:chnum, maxNum:modMaxNum, roomNick:modRoomNick};
				chatService.updateChatRoom(chatRoomObj, function() {
					$("#myRooms").html("");
					showMyRoomList();
					return;
				}, function(err) {
					alert('요청 에러 발생 !');
				});
			}		
		});
	}
	showMyRoomList();
	showMyRequestList();
</script>
<%@include file="../includes/footer.jsp"%>