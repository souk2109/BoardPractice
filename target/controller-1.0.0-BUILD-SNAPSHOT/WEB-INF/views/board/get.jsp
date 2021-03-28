<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
	 
	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-default">
				<div class="panel-heading">
					<i class="fa fa-bell fa-fw"></i>
				</div>
				<div class="panel-body">
					<ul class="chat">
						<li class="left clearfix" data-rno='12'>
							<div>
								<div class="header">
									<strong class="primary-font"></strong>
									<small class="pull-right text-muted"></small>
								</div>
								<p>글입니다.</p>
							</div>
						</li>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="/board002/resources/js/reply.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			
			var bnoValue = '<c:out value="${board.bno}"/>';
			
			showList(1);
			
			function showList(page) {
				var param = {bno:bnoValue, page:page||1};
				replyService.getList(param, function(data) {
					var str = '';
					if(data === null || data.length === 0){
						return;
					}
					
					for(var i=0; i<data.length || 0; i++){
						str += "<li class='left clearfix' data-rno='12'>"+
									"<div>"+
										"<div class='header'>"+
											"<strong class='primary-font'>"+data[i].replyer+"</strong>"+
											"<small class='pull-right text-muted'>"+replyService.displayTime(data[i].updatedate)+"</small>"+
										"</div>"+
										"<p>" + data[i].reply+"</p>"+
									"</div>"+
								"</li>";
						 
					}
					$(".chat").html(str);
				});
			}
			
			 
			
			var str = '';
			replyService.getList(param, function(data) {
				for(var i=0; i<data.length || 0; i++){
					str += "<li class='left clearfix' data-rno='12'>"+
								"<div>"+
									"<div class='header'>"+
										"<strong class='primary-font'>"+data[i].replyer+"</strong>"+
										"<small class='pull-right text-muted'>"+data[i].updatedate+"</small>"+
									"</div>"+
									"<p>" + data[i].reply+"</p>"+
								"</div>"+
							"</li>";
					 
				}
				$(".chat").html(str);
			});
			 
			 
			
			/* ajax통신 테스트 코드
			
			 
			replyService.add({reply:"js test", replyer:"js tester",bno:bnoValue}, 
				function(result) {
					//alert(result);
				}
			);
			
			 
			
			 
			
			
			
			replyService.remove(5, function(result) {
				console.log(result);
			}, function() {
				console.log("실패");
			});
			
			
			
			replyService.get(10, function(ReplyVO) {
				console.log(ReplyVO);
			});
			
			var reply ={rno:11,bno:bnoValue, reply:"수정했따!"};
			replyService.update(reply, function(result) {
				console.log("수정 성공!");
			}, function() {
				console.log("수정 실패");
			});
			
			*/
		})
	</script>
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