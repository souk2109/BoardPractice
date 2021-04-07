<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="../includes/header.jsp"%>
<sec:authentication property="principal" var="user"/>

<form class="form-horizontal" id="mkchatForm" action="/board002/chat/makeChat" method="post">
  <div class="form-group">
    <label class="col-sm-2 control-label">아이디</label>
    <div class="col-sm-10">
      <input type="text" class="form-control" name="id" value="${user.username }" placeholder="아이디" readonly="readonly">
    </div>
  </div>
	<div class="form-group">
		<label class="col-sm-2 control-label">닉네임</label>
		<div class="col-sm-10">
			<input type="text" class="form-control" name="hostNick"
				placeholder="닉네임">
		</div>
	</div>

	<div class="form-group">
		<label class="col-sm-2 control-label">방 이름</label>
		<div class="col-sm-10">
			<input type="text" class="form-control" name="roomNick"
				placeholder="방이름">
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label">설명</label>
		<div class="col-sm-10">
			<textarea class="form-control" style="text-align: left" rows="3" name="content"></textarea>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label">제한 인원</label>
		<div class="col-sm-10">
			<select class="form-control" name="maxNum">
				<c:forEach begin="1" end="10" var="i">
					<option value="${i }">${i}명</option>
				</c:forEach>
			</select>
		</div>
	</div>


	<div class="form-group">
    <div class="col-sm-offset-2 col-sm-10">
      <button type="submit" id="mkchatSubmitBtn" class="btn btn-default">생성하기</button>
    </div>
  </div>
</form>

<script type="text/javascript">
	let mkchatForm = $("#mkchatForm");
	let mkchatSubmitBtn = $("#mkchatSubmitBtn");
	console.log('값 :'+ $(mkchatForm).val());
	// 제한 인원 select로 수정 [2021/04/06]
	$(mkchatSubmitBtn).on("click", function(e) {
		e.preventDefault();
		let hostNick = $("input[name='hostNick']").val().trim();
		let roomNick = $("input[name='roomNick']").val().trim();
		if(hostNick == null || hostNick == ""){
			alert('별명을 입력하세요');
			e.preventDefault();
		}
		else if(roomNick == null || roomNick == ""){
			alert('방 제목을 입력하세요');
			e.preventDefault();
		}else{
			mkchatForm.submit();
		}
		
	});
</script>
<%@include file="../includes/footer.jsp"%>