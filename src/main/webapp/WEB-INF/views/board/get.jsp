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
					<button id="addReplyBtn" class="btn btn-primary btn-xs pull-right">댓글 달기</button>
				</div>
				<div class="panel-body">
					<ul class="chat">
						<li class="left clearfix" data-rno='12'>
							<div>
								<div class="header">
									<strong class="primary-font"></strong>
									<small class="pull-right text-muted"></small>
								</div>
								<p></p>
							</div>
						</li>
					</ul>
				</div>
			</div>
		</div>
	</div>
	
	
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">Modal title</h4>
				</div>
				<div class="modal-body">
					 <div class="form-group">
					 	<label>댓글</label>
					 	<input class="form-control" name="reply" value="새 글">
					 </div>
					 
					 <div class="form-group">
					 	<label>작성자</label>
					 	<input class="form-control" name="replyer" value="새 글">
					 </div>
					 
					 <div class="form-group">
					 	<label>등록일</label>
					 	<input class="form-control" name="replyDate" value="새 글">
					 </div>
				</div>
				<div class="modal-footer">
					<button id="modalModifyBtn" type="button" class="btn btn-default" data-dismiss="modal">변경하기</button>
					<button id="modalRemoveBtn" type="button" class="btn btn-primary">삭제하기</button>
					<button id="modalRegisterBtn" type="button" class="btn btn-primary">등록하기</button>
					<button id="modalCloseBtn" type="button" class="btn btn-primary">닫기</button>
				</div>
			</div>
		</div>
	
	</div>
	
	
	<script type="text/javascript" src="/board002/resources/js/reply.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			
			var bnoValue = '<c:out value="${board.bno}"/>';
			var replyUl = $(".chat");
			showList(1);
			
			function showList(page) {
				var param = {bno:bnoValue, page:page||1};
				replyService.getList(param, function(data) {
					var str = '';
					if(data === null || data.length === 0){
						return;
					}
					
					for(var i=0; i<data.length || 0; i++){
						str += "<li class='left clearfix' data-rno="+data[i].rno+">"+
									"<div>"+
										"<div class='header'>"+
											"<strong class='primary-font'>"+data[i].replyer+"</strong>"+
											"<small class='pull-right text-muted'>"+replyService.displayTime(data[i].replydate)+"</small>"+
										"</div>"+
										"<p>" + data[i].reply+"</p>"+
									"</div>"+
								"</li>";
						 
					}
					$(".chat").html(str);
				});
			}
			
			
			var modal = $(".modal");
			
			var modalReply = modal.find("input[name='reply']");
			var modalReplyer = modal.find("input[name='replyer']");
			var modalReplyDate = modal.find("input[name='replyDate']");
			
			var modalModifyBtn = $("#modalModifyBtn");
			var modalRemoveBtn = $("#modalRemoveBtn");
			var modalRegisterBtn = $("#modalRegisterBtn");
			var modalCloseBtn = $("#modalCloseBtn");
			
			var addReplyBtn = $("#addReplyBtn");
			
			addReplyBtn.on("click", function() {
				modal.find("input").val('');
				modalReplyDate.closest("div").hide();
				modalModifyBtn.hide();
				modalRemoveBtn.hide();
				modalRegisterBtn.show();
				modalReplyer.removeAttr("readonly");
				modal.modal("show");
			});
			
			modalCloseBtn.on("click", function() {
				modal.modal("hide");
			});
			
			modalRegisterBtn.on("click", function(e) {
				replyService.add({reply:modalReply.val(),replyer:modalReplyer.val(),bno:bnoValue}, 
						function(result) {
							modal.find("input").val('');
							showList(1);
						}
					);
				modal.modal("hide");
			});
			
			$(".chat").on("click","li", function() {
				modal.modal("show");
				modalModifyBtn.show();
				modalRemoveBtn.show();
				modalRegisterBtn.hide();
				
				var rno = $(this).data("rno");
				console.log("지금 rno: " + rno);
				modalReplyDate.closest("div").show();
				
				replyService.get(rno, function(ReplyVO) {
					modalReply.val(ReplyVO.reply);
					modalReplyer.val(ReplyVO.replyer).attr("readonly","readonly");
					modalReplyDate.val(replyService.displayTime(ReplyVO.updatedate)).attr("readonly","readonly");
					console.log(ReplyVO);
				});
				
				modalRemoveBtn.on("click", function() {
					replyService.remove(rno, function(result) {
						console.log(result);
						modal.modal("hide");
						showList(1);
					}, function() {
						console.log("실패");
					});
				})
			});
			/* ajax통신 테스트 코드
			
			replyService.remove(5, function(result) {
				console.log(result);
			}, function() {
				console.log("실패");
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