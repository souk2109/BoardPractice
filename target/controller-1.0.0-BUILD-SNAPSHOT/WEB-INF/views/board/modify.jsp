<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../includes/header.jsp" %>
	<div class="row">
		<div class="col-lg-12">
			<h1 class="page-header">수정하기</h1>
		</div>
	</div>
	
	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-default">
				<div class="panel-heading">${board.writer }의 게시글</div>
				<div class="panel-body">
					<form action="/board002/board/modify" method="post">
						<div class="form-group">
							<label>번호</label>
							<input class="form-control" name="bno" value="${board.bno }" readonly="readonly">
						</div>
					
						<div class="form-group">
							<label>제목</label>
							<input class="form-control" name="title" value="${board.title }">
						</div>
						<div class="form-group">
							<label>글 작성</label>
							<textarea class="form-control" rows="3" name="content">${board.content }</textarea>
						</div>
						<div class="form-group">
							<label>작성자</label>
							<input class="form-control" name="writer" readonly="readonly" value="${board.writer }">
						</div>
						
						<div class="form-group">
							<label>등록 날짜</label>
							<input class="form-control" name="regdate" value="${board.regdate }" readonly="readonly">
						</div>
						
						<div class="form-group">
							<label>업데이트 날짜</label>
							<input class="form-control" name="updatadate" value="${board.updatedate }" readonly="readonly">
						</div>
						 
						
						<button type="submit" class="btn btn-default" data-oper="modify">수정하기</button>
						<button class="btn btn-default" data-oper="remove">삭제하기</button>	
						<button class="btn btn-warning" data-oper="list">리스트 보기</button>
						
						<input type='hidden' name='pageNum' value='${criteria.pageNum }'>
						<input type='hidden' name='amount' value='${criteria.amount }'>
					</form>
				</div>
			</div>
		</div>
	</div>
	${criteria.pageNum }
	<script type="text/javascript">
		$(document).ready(function() {
			var formObj = $("form");
			
			$("button").on("click", function(e) {
				e.preventDefault();
				var oper = $(this).data("oper");
				if(oper === "remove"){
					formObj.attr("action", "/board002/board/remove");
				}
				if(oper ==="list"){
					formObj.attr("action","/board002/board/list");
					formObj.attr("method","get");
					formObj.empty();
					formObj.append("<input type='hidden' name='pageNum' value='${criteria.pageNum }'>"
							+"<input type='hidden' name='amount' value='${criteria.amount }'>");
				}
				formObj.submit();
			});

		});
	</script>
	
	
	
	
	
<%@include file="../includes/footer.jsp" %>