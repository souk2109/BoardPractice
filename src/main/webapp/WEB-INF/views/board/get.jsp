<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../includes/header.jsp" %>
	<div class="row">
		<div class="col-lg-12">
			<h1 class="page-header">게시글 보기</h1>
		</div>
	</div>
	
	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-default">
				<div class="panel-heading">${board.writer }의 게시글</div>
				<div class="panel-body">
					<div class="form-group">
						<label>제목</label>
						<input class="form-control" name="title" readonly="readonly" value="${board.title }">
					</div>
					<div class="form-group">
						<label>글 작성</label>
						<textarea class="form-control" rows="3" name="content" readonly="readonly">${board.content }</textarea>
					</div>
					<div class="form-group">
						<label>작성자</label>
						<input class="form-control" name="writer" readonly="readonly" value="${board.writer }">
					</div>
					
					<button class="btn btn-default" data-oper="modify">수정 or 삭제</button>
					<button class="btn btn-warning" data-oper="list">리스트 보기</button>
					
					<form id="operForm" method="get" action="/board002/board/modify">
						<input type="hidden" name="bno" value="${board.bno }">
						<input type="hidden" name="pageNum" value="${criteria.pageNum}">
						<input type="hidden" name="amount" value="${criteria.amount}">
						<input type="hidden" name="type" value="${criteria.type}">
						<input type="hidden" name="keyword" value="${criteria.keyword}">
					</form>
				</div>
			</div>
		</div>
	</div>
	${criteria.pageNum},
	${criteria.amount}
	<script>
		$(document).ready(function() {
			var formObj = $("#operForm");
			
			$("button[data-oper='modify']").on("click", function() {
				formObj.submit();
			});
			
			$("button[data-oper='list']").on("click", function() {
				formObj.attr("action", "/board002/board/list");
				formObj.submit();
			});
		});
	</script>
<%@include file="../includes/footer.jsp" %>