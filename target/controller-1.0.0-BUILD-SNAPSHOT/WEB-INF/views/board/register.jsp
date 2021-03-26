<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../includes/header.jsp" %>
	<div class="row">
		<div class="col-lg-12">
			<h1 class="page-header">Board Register</h1>
		</div>
	</div>
	
	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-default">
				<div class="panel-heading">게시글 등록</div>
				<div class="panel-body">
					<form role="form" action="/board002/board/register" method="post">	
						<div class="form-group">
							<label>제목</label>
							<input class="form-control" name="title">
						</div>
						<div class="form-group">
							<label>글 작성</label>
							<textarea class="form-control" rows="3" name="content"></textarea>
						</div>
						<div class="form-group">
							<label>작성자</label>
							<input class="form-control" name="writer">
						</div>
						<button class="btn btn-warning" type="reset">모두 지우기</button>
						<button class="btn btn-default" type="submit">등록</button>
					</form>
				</div>
			</div>
		</div>
	</div>

<%@include file="../includes/footer.jsp" %>