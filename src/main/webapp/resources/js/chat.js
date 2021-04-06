var chatService = (function() {
	
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
	function getAllChatRooms(callback) {
		$.getJSON("/board002/chat/allChatRoom.json", function(data) {
			if(data){
				callback(data);
			}
		}).fail(function(xhr, status, err) {
			if(error)
				error();
		});
	}
	
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
		displayShortTime : displayShortTime
	};
})();