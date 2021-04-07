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
		console.log("모든 댓글 가져오기!!!");
		var bno = param.bno;
		var page = param.page || 1;
		
		$.getJSON("/board002/reply/pages/"+bno +"/"+page + ".json", function(data) {
			if(data){
				callback(data.list, data.replyCnt);
				console.log(data.list);
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
					callback(result);
					console.log("수정 성공?! : "+result);
				}
			},
			error : function(xhr, status, er) {
				if(error){
					error(er);
				}
			}
		})
	}
	
	function displayTime(timeValue) {
		var today = new Date();
		var gab = today.getTime()-timeValue;
		
		var dateObj = new Date(timeValue);
		 
		// 오늘 등록된 댓글인 경우
		// 60*60*24: 하루의 총 second
		
		if(gab < (60*60*24*1000)){
			var hh = dateObj.getHours();
			var mi = dateObj.getMinutes();
			var ss = dateObj.getSeconds();
			var dateEqual =  dateObj.getDate() === today.getDate() ? true : false;
			if(dateEqual){
				return ['오늘 ',(hh > 9?'':'0')+hh,':',(mi > 9 ? '':'0')+mi,':',(ss > 9 ? '' : '0')+ss].join('');
			}
			// 시 분 초가 10보다 작은 경우 앞에 0을 붙여줌
			return ['어제 ',(hh > 9?'':'0')+hh,':',(mi > 9 ? '':'0')+mi,':',(ss > 9 ? '' : '0')+ss].join('');
		}else{ // 24시간 이상 된 댓글의 경우
			var yy = dateObj.getFullYear();
			var mm = dateObj.getMonth()+1;
			var dd = dateObj.getDate();
			return [yy+'/',(mm > 9 ? '':'0')+mm,'/',(dd > 9 ?'':'0')+dd].join('');
		}
	}
	
	return {
		add : add,
		getList : getList,
		remove : remove,
		get : get,
		update : update,
		displayTime : displayTime
	};
})();
 