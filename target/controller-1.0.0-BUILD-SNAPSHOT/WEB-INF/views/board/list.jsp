<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../includes/header.jsp" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
	<div class="row">
		<div class="col-lg-12">
			<h1 class="page-header">게시판</h1>
		</div>
	</div>
	
	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-default">
				<div class="panel-heading">게시글 리스트
					<button id='regBtn' type="button"  class="btn btn-xs btn-info pull-right">게시글 작성하기</button>
				</div>
			</div>
		</div>
	</div>
	<form id="searchform" method="get" action="/board002/board/list">
		<select name="type">
			<option value="TCW" ${criteria.type eq 'TCW' ?'selected':'' }>전체</option>
			<option value="T" ${criteria.type eq 'T' ?'selected':''}>제목</option>
			<option value="C" ${criteria.type eq 'C' ?'selected':'' }>내용</option>
			<option value="W" ${criteria.type eq 'W' ?'selected':'' }>작성자</option>
			<option value="TC" ${criteria.type eq 'TC' ?'selected':'' }>제목+내용</option>
		</select>
		
		<input name="keyword" type="text" value="${criteria.keyword==null?'':criteria.keyword}">
		<input type="submit" value="검색">
		<input type="hidden" name="pageNum" value="1">
		<input type="hidden" name="amount" value="10">
		<input type="text" value="검색된 개수: ${total }" readonly="readonly">
	</form>
	<table class="table table-striped table-bordered table-hover">
		<thead>
			<tr>
				<th>번호</th>
				<th>제목</th>
				<th>작성자</th>
				<th>작성일</th>
				<th>수정일</th>
			</tr>
		</thead>
		
		<c:forEach items="${list }" var="board">
			<tr>
				<td>${board.bno }</td>
				<td><a class="move" href="${board.bno }">${board.title }</a></td>
				<td>${board.writer }</td>
				<td><fmt:formatDate value="${board.regdate}"/>   </td>
				<td><fmt:formatDate value="${board.updatedate}"/>   </td>
			</tr>
		</c:forEach>
	</table>
	 

	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title" id="myModalLabel">Modal title</h4>
                </div>
                <div class="modal-body">처리가 완료되었습니다.</div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
                    <button type="button" class="btn btn-primary" data-dismiss="modal">Save Change</button>
                </div>
            </div>
        </div>
	</div>
	
	<br>
	
	<c:if test="${pageMaker.prev }">
		<a href="/board002/board/list?pageNum=${pageMaker.startPage-1 }&amount=${criteria.amount}">
			<button class="btn btn-default">prev</button>
		</a>
	</c:if>
	
	<c:forEach begin="${pageMaker.startPage }" end="${pageMaker.endPage }" varStatus="status">
		<a class="paginate_button" href="${status.index }">
			<button class="btn ${status.index eq criteria.pageNum ?'btn-info':'btn-default'}">${status.index }</button>
		</a>
	</c:forEach>
	
	<c:if test="${pageMaker.next}">
		<a class="paginate_button" href="${pageMaker.endPage+1 }">
			<button class="btn btn-default next">next</button>
		</a>
	</c:if>
	
	<form id="pageForm" method="get">
		<input type="hidden" name="pageNum" value="${criteria.pageNum }">
		<input type="hidden" name="amount" value="${criteria.amount }">
		<input type="hidden" name="type" value="${criteria.type}">
		<input type="hidden" name="keyword" value="${criteria.keyword }">
	</form>
	
	<script type="text/javascript">
		$(document).ready(function() {
			var result = '<c:out value="${result}"/>';
			checkModal(result);
			history.replaceState({}, null, null);
			
			function checkModal(result) {
				if(result === '' || history.state){
					return;
				}
				if(parseInt(result) > 0){
					$(".modal-body").html("게시글 " + parseInt(result) + "번이 등록되었습니다.");
				}
				$("#myModal").modal("show");
			}
			
			$("#regBtn").on("click", function() {
				self.location = "/board002/board/register";
			});
			
			var pageForm=$("#pageForm");
			$(".paginate_button").on("click", function(e) {
				e.preventDefault();
				pageForm.attr("action","/board002/board/list");
				pageForm.find("input[name='pageNum']").val($(this).attr("href"));
				pageForm.submit();
			});
			
			$(".move").on("click", function(e) {
				e.preventDefault();
				pageForm.attr("action","/board002/board/get");
				pageForm.append("<input type='hidden' name='bno' value="+$(this).attr('href')+">");
				pageForm.submit();
				
			});
			
			var searchform = $("#searchform");
			searchform.find("input[type='submit']").on("click", function(e) {
				e.preventDefault();
				if(!searchform.find("input[name='keyword']").val()){
					alert('검색 키워드를 입력해주세요!');
				}
				searchform.submit();
			})
		});
		
		 
	</script>
<%@include file="../includes/footer.jsp" %>