var chatService = (function() {
	
	// 내가 만든 채팅방 요청
	function getMyChatRooms(id, callback, error) {
		$.getJSON("/board002/chat/myChatRoom/" + id +".json", function(data) {
			if(data){
				callback(data);
			}
		}).fail(function(xhr, status, err) {
			if(error)
				error();
		});
		
	}
	
	// 모든 채팅방 요청 
	// 단 id를 보내서 사용자 별로 다르게 처리해야함 
	// ex) 로그인한 사용자가 이미 요청한 채팅방이면 '처리 중'으로 표시
	function getAllChatRooms(id, callback) {
		$.getJSON("/board002/chat/allChatRoom/" + id + ".json", function(data) {
			if(data){
				callback(data);
			}
		}).fail(function(xhr, status, err) {
			if(error)
				error();
		});
	}
	
	// 방장이 채팅방 삭제 요청
	function deleteChatRoom(chnum, callback, errpr) {
		$.ajax({
			type : 'delete',
			url : '/board002/chat/delete/'+chnum,
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
		});
	}
	
	// 방장이 채팅방 수정 요청
	function updateChatRoom(chatRoomObj, callback, error) {
		$.ajax({
			type : 'put',
			url : '/board002/chat/update/'+chatRoomObj.chnum,
			data : JSON.stringify(chatRoomObj),
			contentType : "application/json; charset=utf-8",
			success : function(updateResult, status, xhr) {
				if(callback){
					callback(updateResult);
				}
			},
			error : function(xhr, status, err) {
				if(error){
					error(err);
				}
			}
		});
	}

	// 채팅방에 참여신청
	function requestJoinRoom(requestInfo, callback, error) {
		$.ajax({
			type : 'post',
			url : '/board002/chat/request/'+requestInfo.chnum,
			data : JSON.stringify(requestInfo),
			contentType : "application/json; charset=utf-8",
			success : function(requestResult, status, xhr) {
				if(callback){
					callback(requestResult);
				}
			},
			error : function(xhr, status, err) {
				if(error){
					error(err);
				}
			}
		});
	}
	
	// 자신의 방에 참여 신청한 정보들을 가져옴
	function getMyRoomRequest(id, callback) {
		$.getJSON("/board002/chat/getRequests/" + id + ".json", function(data) {
			if(data){
				callback(data);
			}
		}).fail(function(xhr, status, err) {
			if(error)
				error();
		});
	}
	
	// 사용자의 채팅방에 대한 validate를 갱신함
	function updateValidate(validateObj, callback) {
		$.ajax({
			type : 'put',
			url : '/board002/chat/updateValidate',
			data : JSON.stringify(validateObj),
			async: false,
			contentType : "application/json; charset=utf-8",
			success : function(updateResult, status, xhr) {
				if(callback){
					callback(updateResult);
				}
			},
			error : function(xhr, status, err) {
				if(error){
					error(err);
				}
			}
		});
	}
	// 사용자의 채팅방에 대한 validate를 삭제함 
	function deleteValidate(validateObj, callback) {
		$.ajax({
			type : 'delete',
			url : '/board002/chat/deleteValidate',
			data : JSON.stringify(validateObj),
			contentType : "application/json; charset=utf-8",
			success : function(updateResult, status, xhr) {
				if(callback){
					callback(updateResult);
				}
			},
			error : function(xhr, status, err) {
				if(error){
					error(err);
				}
			}
		});
	}
	// tbl_chat_room의 userid를 갱신함 (방장이 허용했을 때 사용자의 id를 추가해줘야함)
	// chnum인 방에 id를 추가할 것이므로 chnum과 id를 보내면 됨 
	function updateUserid(userObj, callback) {
		$.ajax({
			type : 'put',
			url : '/board002/chat/updateUserId',
			data : JSON.stringify(userObj),
			contentType : "application/json; charset=utf-8",
			async: false,
			success : function(updateResult, status, xhr) {
				if(callback){
					callback(updateResult);
				}
			},
			error : function(xhr, status, err) {
				if(error){
					error(err);
				}
			}
		});
	}
	
	function getChatMessageByChnum(chnum, callback) {
		$.getJSON("/board002/chat/getMessage/" + chnum + ".json", function(data) {
			if(data){
				callback(data);
			}
		}).fail(function(xhr, status, err) {
			if(error)
				error();
		});
	}
	
	function displayLongTime(timeValue) {	
		let dateObj = new Date(timeValue);
		let yy = dateObj.getFullYear();
		let mm = dateObj.getMonth()+1;
		let dd = dateObj.getDate();
		let hh = dateObj.getHours();
		let mi = dateObj.getMinutes();
		
		mm = mm < 10 ? '0' + mm : mm;
		dd = dd < 10 ? '0' + dd : dd;
		hh = hh < 10 ? '0' + hh : hh;
		mi = mi < 10 ? '0' + mi : mi;
		return yy + '년 ' + mm + '월 ' + dd + '일' + hh+'시 '+ mi+'분 ';
	}
	 
	 
	function displayShortTime(timeValue) {
		let today = new Date(); // 현재의 시간 (실제 시간)
		let gap = today.getTime()-timeValue; // 등록일 과의 시간 차(시스템 시간)
		let dateObj = new Date(timeValue); // 등록일의 시간 (실제 시간)
		let dateGap = new Date(gap);
		/*
		 * 24시간 이하 차이가 나는 경우
			 오늘 올라온 글인 경우 (-> 오늘, 3시간 전)
			 어제 올라온 글인 경우 (-> 어제 ,3시간 전) [(24-올린시간)+현재시간] 
				 ex) 20시에 올린글을 새벽2시에 봄 (24-20)+2 = 6시간 전
		 24시간 이상 차이가 나는 경우
			 그냥 날짜만 (-> 2021.04.03 )
		*/
		if(gap < 60*60*24*1000){
			let dateEqual =  dateObj.getDate() === today.getDate() ? true : false;
			if(dateEqual){
				let diffTime = today.getHours()- dateObj.getHours();
				if(diffTime == 0){
					return '방금';
				}
				return '오늘 ' + diffTime + '시간 전';
			}else{
				let diffTime = (24-dateObj.getHours()) +today.getHours();
				return '어제 ' + diffTime + '시간 전';
			}
		}else{
			let yy = dateObj.getFullYear();
			let mm = dateObj.getMonth()+1;
			let dd = dateObj.getDate();
			return  yy + '.' + mm +'.' +dd;
		}
	}
	return {
		getMyChatRooms : getMyChatRooms,
		deleteChatRoom : deleteChatRoom, 
		getAllChatRooms : getAllChatRooms,
		displayLongTime : displayLongTime,
		displayShortTime : displayShortTime,
		updateChatRoom : updateChatRoom,
		requestJoinRoom : requestJoinRoom,
		getMyRoomRequest : getMyRoomRequest,
		updateValidate : updateValidate,
		deleteValidate : deleteValidate,
		updateUserid : updateUserid,
		getChatMessageByChnum : getChatMessageByChnum
	};
})();