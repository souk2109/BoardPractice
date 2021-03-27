
console.log("Reply Module");

var replyService = (function() {
	
	// add(reply, callback, error)에서 모든 파라미터를 채울 필요는 없다!
	function add(reply, callback, error) {
		console.log("댓글 추가!");
		$.ajax({
			type : 'post',
			url : '/board002/reply/new',
			data : JSON.stringify(reply),
			contentType : 'application/json; charset=utf-8',
			success : function(result, status, xhr) {
				if(callback){
					callback(result);
				}
			},
			error : function(xhr, status, er) {
				if(error)
					error(er);
			}
		})
	};
	
	function getList(param, callback, error) {
		console.log("모든 댓글 가져오기!");
		var bno = param.bno;
		var page = param.page || 1;
		
		$.getJSON("/board002/reply/pages/"+bno +"/"+page + ".json", function(data) {
			if(callback){
				callback(data);
			}
		}).fail(function(xhr, status, err) {
			if(error)
				error();
		});
		
	}
	
	function remove(rno, callback, error) {
		console.log("댓글 삭제");
		$.ajax({
			type : 'delete',
			url : '/board002/reply/'+rno,
			success : function(deleteResult, status, xhr) {
				if(callback){
					callback(deleteResult);
				}
			},
			error : function(xhr, status, err) {
				if(error){
					error();
				}
			}
		})
	}
	
	function get(rno, callback, error) {
		$.get("/board002/reply/get/"+rno+".json", function(ReplyVO) {
			if(callback){
				callback(ReplyVO);
			}
		}).fail(function(xhr, status, err) {
			if(error){
				err();
			}
		})
	}
	
	function update(reply, callback, error) {
		$.ajax({
			url : '/board002/reply/'+reply.rno,
			type : 'put',
			data : JSON.stringify(reply),
			contentType : "application/json; charset=utf-8",
			success : function(result, status, xhr) {
				if(callback){
					console.log("수정 성공? : "+result);
				}
			},
			error : function(xhr, status, er) {
				if(error){
					error(er);
				}
			}
		})
	}
	
	return {
		add : add,
		getList : getList,
		remove : remove,
		get : get,
		update : update
	};
})();
 